package service;

import java.util.ArrayList;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public void print(ArrayList<String> output) {
        output.forEach(System.out::println);
    }
}