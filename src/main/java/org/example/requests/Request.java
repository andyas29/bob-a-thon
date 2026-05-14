package org.example.requests;

import java.net.HttpURLConnection;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Request {

    public static String sendGetRequest(String urlString) throws Exception {
        URI uri = new URI(urlString);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public static int getSubscribersCount(String json) {
        String subscriberCountBlock = json.split("\"subscriberCount\":")[1];
        String subscriberCountString = subscriberCountBlock.split("\"")[1];
        return Integer.parseInt(subscriberCountString);
    }

}
