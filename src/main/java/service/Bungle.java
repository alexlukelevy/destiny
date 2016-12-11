package service;

import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Bungle {

    public static void main(String[] args) throws IOException, SQLException {
        List<Bucket> buckets = new DestinyLoaderImpl(new PsnDestinyApi(new PsnAuthenticationService(), HttpClients.createDefault()))
                .getInventory(4611686018437367162L, 2305843009227473964L);
        for (Bucket bucket : buckets) {
            System.out.println(bucket.name);
            for (Item item : bucket.items) {
                System.out.println(item.name + " - " + item.lightLevel + " - " + item.grade);
            }
            System.out.println();
        }
    }
}