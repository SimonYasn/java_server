	Action()
{
		
		
		PVCI2 			 ppp;
		PVCI2 			 vvv;
	unsigned short 	status;

	char 			*value 		= NULL;
	char 			*value1 		= NULL;
	char 			*value2 		= NULL;	

	int n = 1;
	int i = 1;
	int sum = 0;
	int randomColumn = 0;
	int             colsize;

    unsigned short outrc;
  
   
    lr_start_transaction("getWeather");
    
    
   ppp = vtc_connect("127.0.0.1", 8888, VTOPT_KEEP_ALIVE);
   vvv = vtc_connect("127.0.0.1", 1010, VTOPT_KEEP_ALIVE);
   
   vtc_column_size( ppp,"city", &colsize );
    randomColumn = rand()%colsize + 1;
    vtc_query_column(ppp, "city", randomColumn , &value);
    vtc_query_column(ppp, "lat", randomColumn , &value1);
    vtc_query_column(ppp, "lon", randomColumn , &value2);
    lr_save_string( value , "city" );
    lr_save_string( value1 , "lat" );
    lr_save_string( value2 , "lon" );
    
  
    
    lr_save_datetime("%Y-%m-%dT00:00:00", DATE_NOW, "dateStart");
    lr_save_datetime("%Y-%m-%dT00:00:00", DATE_NOW+(1*ONE_DAY), "dateEnd");
    lr_save_datetime("%Y-%m-%d", DATE_NOW, "dateToVts");
    
    
		if (strstr("fakeData", lr_eval_string("{city}")) != NULL) {
			
			lr_output_message("%s is not found", lr_eval_string("{city}"));
			
			lr_end_transaction("getWeather", LR_FAIL);

    }
    
    else {
   
   web_service_call( "StepName=NDFDgen_105",
		"SOAPMethod=ndfdXML|ndfdXMLPort|NDFDgen",
		"ResponseParam=response",
		"Service=ndfdXML",
		"ExpectedResponse=SoapResult",
		"Snapshot=t1591723027.inf",
		BEGIN_ARGUMENTS,
		"latitude={lat}",
		"longitude={lon}",
		"product=timeseries",
		"startTime={dateStart}",
		"endTime={dateEnd}",
		"Unit=m",
		"xml:weatherParameters="
			"<weatherParameters>"
				"<maxt>1</maxt>"
				"<mint>1</mint>"
				"<dew>1</dew>"
				"<wspd>1</wspd>"
				"<rh>1</rh>"
			"</weatherParameters>",
		END_ARGUMENTS,
		BEGIN_RESULT,
		"NDFDgenResult=Param_NDFDgenResult",
		END_RESULT,
		LAST);
    
    
    //MINTEMP
    lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=Daily Minimum Temperature&lt;/name&gt;\n        &lt;value&gt;(.*?)&lt",
          "Ordinal=1",
          "ResultParam=minTemp",
          LAST);
    
    //MAXTEMP
    lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=Maximum Temperature&lt;/name&gt;\n        &lt;value&gt;(.*?)&lt",
          "Ordinal=1",
          "ResultParam=maxTemp",
          LAST);
    
    //Catch and find avarage dew point////////////////////////////////////////////////////
    
    lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=Dew Point Temperature&lt;\/name&gt;\\n        &lt;value&gt;([-\\d&lt;\/value&gt;\\s]*)&lt;\/value&gt;\\n      &lt;\/temperature&gt;",
          "Ordinal=1",
          "ResultParam=AllDewPoints",
          LAST);
    
    lr_save_param_regexp (
          lr_eval_string("{AllDewPoints}"),
          strlen(lr_eval_string("{AllDewPoints}")),
          "RegExp=(-?\\d+)",
          "Ordinal=All",
          "ResultParam=DewPoints",
          LAST);
    
    n = lr_paramarr_len("DewPoints");
    i = 1;
    
    while (i<=n) {
    	sum = sum + atoi(lr_eval_string(lr_paramarr_idx("DewPoints", i)));
    	i++;
    }
    lr_save_int(sum/n, "avgDewPoint");
//////////////////////////////////////////////////////////////////////////////////////////////

//Catch and find avarage Wind Speed////////////////////////////////////////////////////
    
    lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=Wind Speed&lt;\/name&gt;\\n        &lt;value&gt;([-\\d&lt;\/value&gt;\\s]*)&lt;\/value&gt;\\n      &lt;\/wind-speed&gt;",
          "Ordinal=1",
          "ResultParam=AllWindSpeed",
          LAST);
    
    lr_save_param_regexp (
          lr_eval_string("{AllWindSpeed}"),
          strlen(lr_eval_string("{AllWindSpeed}")),
          "RegExp=(-?\\d+)",
          "Ordinal=All",
          "ResultParam=WindSpeed",
          LAST);
    
    n = lr_paramarr_len("WindSpeed");
    i = 1;
    sum = 0;
    
    while (i<=n) {
    	sum = sum + atoi(lr_eval_string(lr_paramarr_idx("WindSpeed", i)));
    	i++;
    }
    lr_save_int(sum/n, "avgWindSpeed");
//////////////////////////////////////////////////////////////////////////////////////////////

//Catch and find avarage Humidity////////////////////////////////////////////////////
    
    lr_save_param_regexp (
          lr_eval_string("{response}"),
          strlen(lr_eval_string("{response}")),
          "RegExp=Relative Humidity&lt;\/name&gt;\\n        &lt;value&gt;([-\\d&lt;\/value&gt;\\s]*)&lt;\/value&gt;\\n      &lt;\/humidity&gt;",
          "Ordinal=1",
          "ResultParam=AllHumidity",
          LAST);
    
    lr_save_param_regexp (
          lr_eval_string("{AllHumidity}"),
          strlen(lr_eval_string("{AllHumidity}")),
          "RegExp=(-?\\d+)",
          "Ordinal=All",
          "ResultParam=Humidity",
          LAST);
    
    n = lr_paramarr_len("Humidity");
    i = 1;
    sum = 0;
    
    while (i<=n) {
    	sum = sum + atoi(lr_eval_string(lr_paramarr_idx("Humidity", i)));
    	i++;
    }
    lr_save_int(sum/n, "avgHumidity");
//////////////////////////////////////////////////////////////////////////////////////////////
    
vtc_send_message(vvv, "city", lr_eval_string("{city}"),&status);
vtc_send_message(vvv, "date", lr_eval_string("{dateToVts}"),&status);
vtc_send_message(vvv, "maxTemp", lr_eval_string("{maxTemp}"),&status);
vtc_send_message(vvv, "minTemp", lr_eval_string("{minTemp}"),&status);
vtc_send_message(vvv, "humidity", lr_eval_string("{avgHumidity}"),&status);
vtc_send_message(vvv, "windSpeed", lr_eval_string("{avgWindSpeed}"),&status);
vtc_send_message(vvv, "dewPoint", lr_eval_string("{avgDewPoint}"),&status);
   
lr_end_transaction("getWeather", LR_PASS); 

    }
   
    vtc_disconnect(ppp);
    vtc_disconnect(vvv);

	return 0;
}
