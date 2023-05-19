package com.interviews.lseg.taskfour.client;

import reactor.netty.http.client.HttpClient;

public class OkgClient {

    private static final String BASE_URL = "https://www.okg.se";
    private static final String REACTOR_URI = "/.netlify/functions/getReactorOutput";

    private HttpClient client;

    public OkgClient() {
        client = HttpClient
                .create()
                .baseUrl(BASE_URL);
    }

    public String getReactorData() {
        return client
                .get()
                .uri(REACTOR_URI)
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }


}
