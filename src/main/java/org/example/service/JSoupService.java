package org.example.service;

import org.example.models.UniversityWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class JSoupService {
    private final static String WorkerURL = "https://cs.pollub.pl/staff/";
    private final static String DeanHoursURL = "https://weii.pollub.pl/aktualnosci/";

    public static Document download(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static List<UniversityWorker> getWorkers() throws IOException {
        Document d = download(WorkerURL);

        List<UniversityWorker> workerList = new ArrayList<>();

        Elements departments = d.select("h3");

        for(Element departament: departments){
            String dept = departament.text().trim().toLowerCase();

            Element next = departament.nextElementSibling();

            while( next!=null && !next.tagName().matches("h3")) {
                Elements names = next.select("li");

                for(Element name: names) {
                    String n = name.text().trim();

                    if(!n.isEmpty()){
                        workerList.add(new UniversityWorker(dept,n));
                    }
                }
                next = next.nextElementSibling();
            }
        }

        workerList.sort(Comparator
                .comparing(
                        UniversityWorker::getDepartment, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(
                        UniversityWorker::getName, String.CASE_INSENSITIVE_ORDER)
        );

        return workerList;
    }

    public static List<UniversityWorker> getDoctors() throws IOException {
        List<UniversityWorker> w = getWorkers();
        List<UniversityWorker> doctorsList = new ArrayList<>();

        for( UniversityWorker worker: w){
            String name = worker.getName().toLowerCase();

            if(name.startsWith("dr ")){
                doctorsList.add(worker);
            }
        }
        return doctorsList;
    }
}
