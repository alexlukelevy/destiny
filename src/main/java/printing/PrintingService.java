package printing;

import java.util.HashMap;

public interface PrintingService {

    void print(String output);

    void print(HashMap<String, String> hashMap);

}