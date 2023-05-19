package com.interviews.lseg.taskfour;

import com.interviews.lseg.taskfour.client.OteClient;
import com.interviews.lseg.taskfour.model.ElectricityImbalances;
import com.interviews.lseg.taskfour.model.ElectricityImbalancesMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TaskFourApplication {
    public static final String URL = "https://www.ote-cr.cz/en/statistics/electricity-imbalances";

    public static void main(String[] args) {
        OteClient client = new OteClient();
        ElectricityImbalancesMapper mapper = new ElectricityImbalancesMapper();
        try {
            Document doc = Jsoup.connect(URL).get();
            String fileUri = doc.select(".report_attachment_links a").get(0).attr("href");
            byte[] imbalances = client.getImbalances(fileUri);
            ElectricityImbalances result = mapper.parse(imbalances);
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println("Can't get table due to: " + ex);
        }
    }
}
