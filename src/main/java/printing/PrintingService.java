package printing;

import entities.OptimisedInventory;

public interface PrintingService {

    void print(String output);

    void print(OptimisedInventory inventory);

}