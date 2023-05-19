package com.interviews.lseg.taskfour;

import com.interviews.lseg.taskfour.client.HurDat2Client;
import com.interviews.lseg.taskfour.model.HurData2Mapper;
import com.interviews.lseg.taskfour.service.HurData2Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class TaskFourApplication {

    public static void main(String[] args) {
        var hurDat2Client = new HurDat2Client();
        var hurData2Mapper = new HurData2Mapper();
        var hurData2Service = new HurData2Service(hurData2Mapper);
        var client = hurDat2Client.getClient();
        var textResponse = client
                .get()
                .uri("/data/hurdat/hurdat2-nepac-1949-2016-041317.txt")
                .responseContent()
                .aggregate()
                .asByteArray()
                .block();
        if(textResponse != null) {
            try (var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(textResponse)))) {
                hurData2Service.processWithFilter(reader, it -> (it.year() >= 2015) && (it.name().endsWith("A")))
                        .forEach(System.out::println);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("Response is empty.");
        }
    }
}
