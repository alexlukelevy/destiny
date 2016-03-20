package service;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }
}