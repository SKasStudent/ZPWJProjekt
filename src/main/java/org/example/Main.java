package org.example;

import org.example.dto.DrzewoDTO;
import org.example.encje.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    static void main() throws Exception {
        /*
        //Lab 1
        String[] lista1= {"Brzoza", "Dab", "Buk", "Topola", "Sosna","Dab", "Olszyna", "Brzoza", "Brzoza1"};
        //1
        System.out.println("zad1");
        Stream<String> stream1 = Arrays.stream(lista1).distinct().filter(s -> s.contains("Topola"));
        stream1.forEach(System.out::println);
        System.out.println();
        //2
        System.out.println("zad2");
        Map<String,Long> wynik = Arrays.stream(lista1).collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        wynik.forEach((nazwa, ilosc)->{
            if(ilosc>1){
                System.out.println(nazwa+" "+ilosc);
            }
        });
        Long powtorzenia = Arrays.stream(lista1).count()-Arrays.stream(lista1).distinct().count();
        System.out.println(powtorzenia);
        System.out.println();
        //3
        System.out.println("zad3");
        //String[] lista2 = Arrays.stream(lista1).map(String::toLowerCase).toArray(String[]::new);
        //Stream<String> stream2 = Arrays.stream(lista2);
        Stream<String> stream2 = Arrays.stream(lista1).map(String::toLowerCase);
        stream2.forEach(System.out::println);
        System.out.println();
        //4
        System.out.println("zad4");

        File file = new File("Zapis.txt");

        System.out.println("Zapis do pliku");

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (String s : lista1) {
                fileWriter.write(s + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Odczyt z pliku");

        try {
            Path path = Paths.get("Zapis.txt");

            for (String linia : Files.readAllLines(path)) {
                System.out.println(linia);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println();
        //5
        System.out.println("zad5");
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Stream<String> stream3 = bufferedReader.lines().distinct().filter(s -> s.contains("Brzoza")).sorted();
            stream3.forEach(System.out::println);

        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        */
        /*
        //Lab 2 Z1
        XmlHandler handler = new XmlHandler();
        Path path = Path.of("WoodCatalogue.xml");

        List<Wood> woods = List.of(
                new Wood("Dąb", "Liściaste", "Polska", "Jasnobrązowy", 17.12),
                new Wood("Sosna", "Iglaste", "Szwecja", "Żółtawy", 124.54),
                new Wood("Mahoń", "Liściaste", "Honduras", "Czerwonobrązowy", 45.34),
                new Wood("Brzoza", "Liściaste", "Polska", "Kremowy", 54.23)
        );

        //handler.createXml(woods, path);

        System.out.println("XML:");
        try {
            System.out.println(Files.readString(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("LISTA Z XML:");
        handler.readXml(path).forEach(System.out::println);
        */
        //LAB4

        //zad1
        System.out.println("zad1");

        Drzewo drzewo = new Drzewo(1L, "Sosna", 18.5, 40.0, false);
        Pracownik pracownik = new Pracownik(1L, "Jan", "Pilarz", 5200.0);
        Deska deska = new Deska(1L, "Sosna", 2.5, 0.15, 0.025);

        System.out.println("Drzewo: " + drzewo.getGatunek());
        System.out.println("Pracownik: " + pracownik.getImie());
        System.out.println("Drzewo: " + deska.getDlugosc());


        //zad2
        System.out.println("zad2");
        DrzewoBuilder drzewoB = DrzewoBuilder.builder()
                .id(1L)
                .gatunek("Dab")
                .wysokosc(18.5)
                .srednica(45.0)
                .sciete(false)
                .build();
        PracownikBuilder pracownikB = PracownikBuilder.builder()
                .id(1L)
                .imie("Jan")
                .stanowisko("Pilarz")
                .pensja(5200.0)
                .build();
        DeskaBuilder deskaB = DeskaBuilder.builder()
                .id(1L)
                .gatunekDrewna("Sosna")
                .dlugosc(2.5)
                .szerokosc(0.15)
                .grubosc(0.025)
                .build();

        System.out.println("Drzewo: " + drzewoB.getGatunek());
        System.out.println("Pracownik: " + pracownikB.getImie());
        System.out.println("Drzewo: " + deskaB.getDlugosc());

        //zad3
        System.out.println("zad3");
        Drzewo drzewo1 = new Drzewo(
                10L, "Sosna", 15.0, 30.0, false
        );

        DrzewoDTO kopia = DrzewoDTO.mapuj(drzewo1);

        System.out.println("Przed zmiana: " + drzewo1.getGatunek() + ", " + drzewo1.isSciete());

        drzewo1.setGatunek("Dab");
        drzewo1.setSciete(true);

        System.out.println("Po zmianie: " + drzewo1.getGatunek() + ", " + drzewo1.isSciete());

        drzewo1 = kopia.przywrocEncje();

        System.out.println("Po wycofaniu: " + drzewo1.getGatunek() + ", " + drzewo1.isSciete());
    }
}
