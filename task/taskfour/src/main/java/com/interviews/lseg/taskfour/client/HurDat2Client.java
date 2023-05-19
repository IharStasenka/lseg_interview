package com.interviews.lseg.taskfour.client;

import reactor.netty.http.client.HttpClient;

public class HurDat2Client {

    public HttpClient getClient() {
       return HttpClient
               .create()
               .baseUrl("https://www.nhc.noaa.gov");
    }
}
