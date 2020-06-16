vuser_end()
{
	PVCI			 ppp;
	unsigned short outrc;
	
	ppp = vtc_connect("127.0.0.1", 8888, VTOPT_KEEP_ALIVE);
	vtc_clear_column(ppp, "city", &outrc);
    vtc_clear_column(ppp, "lat", &outrc);
    vtc_clear_column(ppp, "lon", &outrc);
    vtc_disconnect(ppp);
    
	return 0;
	
}
