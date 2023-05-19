package com.interviews.lseg.taskfour;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;

public class TaskFourApplication {

    public static final String TABLE_NAME = "StatisticsElectricityImbalanceTable.html";
    public static final String URL = "https://www.ote-cr.cz/en/statistics/electricity-imbalances";

    public static void main(String[] args) {
        try(var fileStream = new FileOutputStream(TABLE_NAME)) {
            Document doc = Jsoup.connect(URL).get();
            Elements tableElements = doc.select("table");
            fileStream.write(tableElements.outerHtml().getBytes());
        } catch (Exception ex) {
            System.out.println("Can't get table due to: " + ex);
        }
    }
}
