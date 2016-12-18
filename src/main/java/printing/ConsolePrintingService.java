package printing;

import java.util.HashMap;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public void print(HashMap<String, String> hashMap) {
        for(String key : hashMap.keySet()) {
            print(key + ": " + hashMap.get(key));
        }
    }

}