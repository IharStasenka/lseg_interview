package com.interviews.lseg.taskfour.client;

import reactor.netty.http.client.HttpClient;

public class OteClient {
    public static final String BASE_URL = "https://www.ote-cr.cz";

    private HttpClient client;
    public OteClient() {
        client = HttpClient.create().followRedirect(true);
    }

    public byte[] getImbalances(String fileUri) {
        return client
                .get()
                .uri(BASE_URL + fileUri)
                .responseContent()
                .aggregate()
                .asByteArray()
                .block();
    }

}
