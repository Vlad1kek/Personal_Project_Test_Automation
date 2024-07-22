package utils.run;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
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

            // Обработка JSON-ответа
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);

            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode billNode : dataNode) {
                    String billId = billNode.get("id").asText();
                    listId.add(billId);
                }
            }

            if (response.statusCode() == 401) {
                throw new RuntimeException("Authorization does not work with token:" + token);
            } else if (response.statusCode() != 200) {
                throw new RuntimeException("Something went wrong while get data " + response.statusCode());
            }
            System.out.println("Response: " + jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteHttp(String url) {
        try {
            if (!listId.isEmpty()) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .headers(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .DELETE()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 204) {
                    throw new RuntimeException("Something went wrong while clearing data " + response.statusCode());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteBills() {
        getHttp("http://localhost/api/v1/bills");
        if (!listId.isEmpty()) {
            for (String id : listId) {
                deleteHttp(String.format("http://localhost/api/v1/bills/%s", id));
            }
            listId.clear();
        }
    }

    public static void deleteBudgets() {
        getHttp("http://localhost/api/v1/budgets");
        if (!listId.isEmpty()) {
            for (String id : listId) {
                deleteHttp(String.format("http://localhost/api/v1/budgets/%s", id));
            }
            listId.clear();
        }
    }

    public static void deleteAccounts() {
        getHttp("http://localhost/api/v1/accounts");
        if (!listId.isEmpty()) {
            for (String id : listId) {
                deleteHttp(String.format("http://localhost/api/v1/accounts/%s", id));
            }
            listId.clear();
        }
    }

    public static void deletePiggyBanks() {
        getHttp("http://localhost/api/v1/piggy-banks");
        if (!listId.isEmpty()) {
            for (String id : listId) {
                deleteHttp(String.format("http://localhost/api/v1/piggy-banks/%s", id));
            }
            listId.clear();
        }
    }

    public static void clearData() {
        deleteBills();
        deleteBudgets();
        deleteAccounts();
        deletePiggyBanks();
    }
}