package com.interviews.lseg.taskfour;

import com.interviews.lseg.taskfour.client.OkgClient;
import com.interviews.lseg.taskfour.model.ReactorDataMapper;

public class TaskThreeApplication {

    public static void main(String[] args) {
        var okgClient = new OkgClient();
        var mapper = new ReactorDataMapper();
        var textResponse = okgClient.getReactorData();
        var data = mapper.parse(textResponse);
        System.out.println(data);
    }
}
