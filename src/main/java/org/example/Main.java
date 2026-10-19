package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static void main() {
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
    }
}
