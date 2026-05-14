package org.example.requests;

import java.net.HttpURLConnection;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Utility class for making HTTP requests and processing API responses.
 * <p>
 * This class provides methods for sending GET requests to external APIs
 * (such as YouTube Data API v3) and parsing JSON responses to extract
 * specific data like subscriber counts.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class Request {

    /**
     * Sends an HTTP GET request to the specified URL and returns the response as a string.
     * <p>
     * This method establishes an HTTP connection, sends a GET request, and reads
     * the response body. If the response code is not HTTP 200 OK, a RuntimeException
     * is thrown.
     * </p>
     *
     * @param urlString the URL to send the GET request to
     * @return the response body as a string
     * @throws Exception if an error occurs during the request or if the response code
     *                   is not HTTP 200 OK
     */
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

    /**
     * Extracts the subscriber count from a YouTube API JSON response.
     * <p>
     * This method parses a JSON string returned by the YouTube Data API v3
     * and extracts the subscriber count value. The parsing is done using
     * simple string splitting operations.
     * </p>
     *
     * @param json the JSON response string from the YouTube Data API
     * @return the subscriber count as an integer
     * @throws NumberFormatException if the subscriber count cannot be parsed as an integer
     * @throws ArrayIndexOutOfBoundsException if the JSON structure is unexpected
     */
    public static int getSubscribersCount(String json) {
        String subscriberCountBlock = json.split("\"subscriberCount\":")[1];
        String subscriberCountString = subscriberCountBlock.split("\"")[1];
        return Integer.parseInt(subscriberCountString);
    }

}
