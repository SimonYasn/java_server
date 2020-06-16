vuser_init()
{
	
	char *param;
		char *cities[200];
		char *latlon[200];
		int first;
		int last;
		PVCI			 ppp;
	unsigned short 	status;
	int 			rc;
	char 			*value 		= NULL;
	char 			**colnames 	= NULL;
	char 			**rowdata 	= NULL;
	int n = 1;
	int i = 1;
	int random = 0;
	
		

        //connect to vts
		ppp = vtc_connect("127.0.0.1", 8888, VTOPT_KEEP_ALIVE);
		
		
		//call soap request for catching all cities
		
		web_service_call( "StepName=LatLonListCityNames_108",
		"SOAPMethod=ndfdXML|ndfdXMLPort|LatLonListCityNames",
		"ResponseParam=response",
		"Service=ndfdXML",
		"ExpectedResponse=SoapResult",
		"Snapshot=t1591676593.inf",
		BEGIN_ARGUMENTS,
		"displayLevel=1",
		END_ARGUMENTS,
		BEGIN_RESULT,
		END_RESULT,
		LAST);

		

	//catch string with cities from response
    
     lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=cityNameList&gt;(.*?)&lt",
          "Ordinal=1",
          "ResultParam=cities",
          LAST);
	
	//parse this string
    
    lr_save_param_regexp (
          lr_eval_string("{cities}"),
          strlen(lr_eval_string("{cities}")),
          "RegExp=([a-zA-Z]+,[a-zA-Z]+)",
          "Ordinal=All",
          "ResultParam=cities1",
          LAST);
    
	
	//catch string with latlon from response
	
     lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=latLonList&gt;(.*?)&lt",
          "Ordinal=1",
          "ResultParam=latlon",
          LAST);
    
	//parse this string
	
    lr_save_param_regexp (
          lr_eval_string("{latlon}"),
          strlen(lr_eval_string("{latlon}")),
          "RegExp=([-+]?[0-9]*\\.?[0-9]+,[-+]?[0-9]*\\.?[0-9]+)",
          "Ordinal=All",
          "ResultParam=latlon1",
          LAST);
    
    //put cities to VTS
    
    n = lr_paramarr_len("cities1");
	
	while (i <= n) {
		
	vtc_send_message(ppp, "city", lr_eval_string(lr_paramarr_idx("cities1", i)),&status);
	i++;
    }
    
    
    //put lat and lon to VTS
    
    n = lr_paramarr_len("latlon1");
    i = 1;
    
    while (i <= n) {
    	
    	lr_save_param_regexp (
          lr_eval_string(lr_paramarr_idx("latlon1", i)),
          strlen(lr_eval_string(lr_paramarr_idx("latlon1", i))),
          "RegExp=([-+]?[0-9]*.?[0-9]+)",
          "Ordinal=All",
          "ResultParam=latANDlon",
          LAST);
    	
    	vtc_send_message(ppp, "lat", lr_eval_string(lr_paramarr_idx("latANDlon", 1)),&status);
    	vtc_send_message(ppp, "lon", lr_eval_string(lr_paramarr_idx("latANDlon", 2)),&status);
    	
    	i++;
    	
    }
    
    
    
    
   //add fake values
   i = 1;
   n = 15;
   while (i <= n) {   	
   	vtc_send_message(ppp, "city", "fakeData",&status);
    vtc_send_message(ppp, "lat", "fakeData",&status);
    vtc_send_message(ppp, "lon", "fakeData",&status);
    i++; 	
   }
   
   
   vtc_disconnect(ppp);
	return 0;

	

}
