package org.example.service;

import org.example.models.DeanHours;
import org.example.models.UniversityWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSoupService {
    private final static String WorkerURL = "https://cs.pollub.pl/staff/";
    private final static String DeanHoursURL = "https://weii.pollub.pl/aktualnosci/page";

    public static Document download(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static List<UniversityWorker> getWorkers() throws IOException {
        Document d = download(WorkerURL);

        List<UniversityWorker> workerList = new ArrayList<>();

        Elements headings = d.select("h3");

        headings.removeIf(heading ->
                !heading.text().trim().startsWith("Zakład")
        );

        for(Element departament: headings){
            String dept = departament.text().trim().toLowerCase();

            Element next = departament.nextElementSibling();

            while( next!=null && !next.tagName().matches("h3")) {
                Elements names = next.select("a");

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

    public static List<DeanHours> getDeanHours() throws IOException {
        List<DeanHours> godzinki = new ArrayList<>();

        String url;

        for(int i = 1; i < 10; i++){

            if(i==1){
                url = DeanHoursURL;
            }
            else{
                url = DeanHoursURL + i + ".html";
            }

            Document document = download(url);

            Elements links = document.select("a");

            for(Element link:links) {
                String title = link.text().trim().toLowerCase();

                if(!title.contains("godziny wolne od")) continue;

                LocalDate date = parseDate(title);

                godzinki.add(new DeanHours(title, date));
            }
        }
        return godzinki;
    }

    public static LocalDate parseDate(String text){
        Pattern pattern = Pattern.compile(
                "(\\d{1,2})\\s+" +
                        "(stycznia|lutego|marca|kwietnia|maja|czerwca|" +
                        "lipca|sierpnia|września|października|listopada|grudnia)" +
                        "\\s+(\\d{4})",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(text);

        if(!matcher.find()) return null;

        int day = Integer.parseInt(matcher.group(1));
        String monthN = matcher.group(2).toLowerCase();
        int year = Integer.parseInt(matcher.group(3));

        int month = 0;

        switch(monthN){
            case "stycznia": month = 1; break;
            case "lutego": month = 2; break;
            case "marca": month = 3; break;
            case "kwietnia": month = 4; break;
            case "maja": month = 5; break;
            case "czerwca": month = 6; break;
            case "lipca": month = 7; break;
            case "sierpnia": month = 8; break;
            case "września": month = 9; break;
            case "października": month = 10; break;
            case "listopada": month = 11; break;
            case "grudnia": month = 12; break;
        }

        try{
            return LocalDate.of(year,month,day);
        }catch(Exception e){
            return null;
        }
    }
}
