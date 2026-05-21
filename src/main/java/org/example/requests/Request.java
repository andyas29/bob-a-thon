package org.example.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Utility class for making HTTP requests and processing API responses.
 * <p>
 * This class provides methods for sending GET requests to external APIs
 * (such as YouTube Data API v3) and parsing JSON responses to extract
 * specific data like subscriber counts.
 * </p>
 *
 * @author Bob
 * @version 1.0
 */
public class Request {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Request() {
    }

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
     * Bob made: Refactored to use Jackson for safe JSON parsing instead of brittle string splitting.
     * This method now properly handles missing fields and unexpected response structures.
     * </p>
     * <p>
     * This method parses a JSON string returned by the YouTube Data API v3
     * and extracts the subscriber count value using Jackson for safe JSON parsing.
     * The method handles missing fields and unexpected response structures gracefully.
     * </p>
     * <p>
     * Expected JSON structure:
     * </p>
     * <pre>
     * {
     *   "items": [
     *     {
     *       "statistics": {
     *         "subscriberCount": "12345"
     *       }
     *     }
     *   ]
     * }
     * </pre>
     *
     * @param json the JSON response string from the YouTube Data API
     * @return the subscriber count as an integer
     * @throws IllegalArgumentException if the JSON is invalid, missing required fields,
     *                                  or the subscriber count cannot be parsed
     */
    public static int getSubscribersCount(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            
            // Validate that the response contains items array
            JsonNode items = root.path("items");
            if (items.isMissingNode() || !items.isArray() || items.isEmpty()) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: 'items' array is missing or empty"
                );
            }
            
            // Get the first item
            JsonNode firstItem = items.get(0);
            if (firstItem == null) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: first item is null"
                );
            }
            
            // Navigate to statistics.subscriberCount
            JsonNode statistics = firstItem.path("statistics");
            if (statistics.isMissingNode()) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: 'statistics' field is missing"
                );
            }
            
            JsonNode subscriberCount = statistics.path("subscriberCount");
            if (subscriberCount.isMissingNode()) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: 'subscriberCount' field is missing"
                );
            }
            
            // Parse the subscriber count
            String subscriberCountText = subscriberCount.asText();
            if (subscriberCountText == null || subscriberCountText.isEmpty()) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: 'subscriberCount' is empty"
                );
            }
            
            try {
                return Integer.parseInt(subscriberCountText);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Invalid YouTube API response: 'subscriberCount' value '" +
                    subscriberCountText + "' is not a valid integer", e
                );
            }
            
        } catch (IOException e) {
            throw new IllegalArgumentException(
                "Failed to parse JSON response: " + e.getMessage(), e
            );
        }
    }

}
