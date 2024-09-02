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

public class ClearData {
    private static String token;
    private static final List<String> listId = new ArrayList<>();

    public static void getToken() {
        if (token == null || token.isEmpty()) {
            if (ProjectProperties.isServerRun()) {
                token = FireflyUtils.token;
            } else {
                token = ProjectProperties.getPropToken();
            }
        }
    }

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
                listId.clear();
                for (JsonNode billNode : dataNode) {
                    String billId = billNode.get("id").asText();
                    listId.add(billId);
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

    public static void deleteResource(String resourceType, int minIdForDeletion) {
        getHttp(String.format("%s/api/v1/%s",
                ProjectProperties.url(), resourceType));

        if (!listId.isEmpty()) {
            for (String id : listId) {
                int intId = Integer.parseInt(id);
                if (intId > minIdForDeletion) {
                    deleteHttp(String.format("%s/api/v1/%s/%s",
                            ProjectProperties.url(), resourceType, id));
                }
            }
            listId.clear();
        }
    }

    public static void clearData() {
        deleteResource("bills", 0);
        deleteResource("budgets", 0);
        deleteResource("users", 2);
    }
}