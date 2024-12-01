package backend.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApartmentService {
    private static final String API_URL = "http://localhost:5000/api/apartments";

    public static String getApartments() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString(); // JSON response
    }

    public static void main(String[] args) {
        try {
            String apartments = getApartments();
            System.out.println("Apartments: " + apartments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
