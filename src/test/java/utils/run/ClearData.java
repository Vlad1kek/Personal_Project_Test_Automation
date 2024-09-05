package utils.run;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import utils.log.LogUtils;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing and clearing data within the Firefly III application
 * using HTTP requests. This class provides methods to handle token retrieval,
 * send GET and DELETE requests, and perform bulk deletion of resources like
 * bills, budgets, and users.
 * <p>
 * The class uses the token to authenticate requests and processes the responses
 * to manage and delete specified resources. It is particularly useful for
 * cleaning up test data or resetting certain entities in the application.
 */
public class ClearData {
    private static String token;
    private static final List<String> LIST_ID = new ArrayList<>();

    /**
     * Retrieves and sets the authorization token for HTTP requests.
     * If the token is already available, it reuses it. If not, it fetches
     * the token from either the server environment or local properties.
     */
    public static void getToken() {
        if (token == null || token.isEmpty()) {
            if (ProjectProperties.isServerRun()) {
                token = FireflyUtils.token;
            } else {
                token = ProjectProperties.getPropToken();
            }
        }
    }

    /**
     * Sends an HTTP GET request to the specified URL and processes the JSON response.
     * Extracts the IDs from the response and stores them in a list for further processing.
     *
     * @param url the API endpoint to send the GET request to.
     * @throws RuntimeException if the authorization fails or if the response status
     *                          code is not 200.
     */
    public static void getHttp(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();

            // Processing JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);

            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null && dataNode.isArray()) {
                LIST_ID.clear();
                for (JsonNode billNode : dataNode) {
                    String billId = billNode.get("id").asText();
                    LIST_ID.add(billId);
                }
            }

            if (response.statusCode() == 401) {
                throw new RuntimeException("Authorization does not work with token:" + token);
            } else if (response.statusCode() != 200) {
                throw new RuntimeException("Something went wrong while getting data. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            LogUtils.logFatal("Error occurred while sending GET request: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an HTTP DELETE request to the specified URL to delete a resource.
     * Logs a warning if the deletion fails and throws an exception if the
     * response status code is not 204.
     *
     * @param url the API endpoint to send the DELETE request to.
     * @throws RuntimeException if the deletion fails or if an error occurs
     *                          during the request.
     */
    public static void deleteHttp(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                LogUtils.logWarning("Failed to delete resource at: " + url + " Status code: " + response.statusCode());
                throw new RuntimeException("Failed to delete resource. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            LogUtils.logFatal("Error occurred while sending DELETE request: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes resources of a specified type if their IDs exceed a given minimum ID.
     *
     * @param resourceType the type of resource to delete (e.g., "bills", "budgets").
     * @param minIdForDeletion the minimum ID value above which resources will be deleted.
     */
    public static void deleteResource(String resourceType, int minIdForDeletion) {
        getHttp(String.format("%s/api/v1/%s",
                ProjectProperties.url(), resourceType));

        if (!LIST_ID.isEmpty()) {
            for (String id : LIST_ID) {
                int intId = Integer.parseInt(id);
                if (intId > minIdForDeletion) {
                    deleteHttp(String.format("%s/api/v1/%s/%s",
                            ProjectProperties.url(), resourceType, id));
                }
            }
            LIST_ID.clear();
        }
    }

    /**
     * Clears specific data types within the Firefly III application by deleting all
     * bills, budgets, and users (except for users with IDs less than or equal to 2).
     */
    public static void clearData() {
        deleteResource("bills", 0);
        deleteResource("budgets", 0);
        deleteResource("users", 2);
    }
}