package utils.run;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ClearData {
    private static String Token;
    private static final List<String> listId = new ArrayList<>();

    public static void Token() {
        if (Token == null) {
            if (ProjectProperties.isServerRun()) {
                Token = FireflyUtils.Token;
            }
            else {
                Token = ProjectProperties.getPropToken();
            }
        }
    }

    public static void getHttp(String endpoint) {
        try {
            Response response = given()
                    .headers(HttpHeaders.AUTHORIZATION, "Bearer " + Token)
                    .pathParam("endpoint", endpoint)
                    .when()
                    .get("http://localhost:80/api/v1/{endpoint}");

            String jsonString = response.asString();
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
                throw new RuntimeException("Authorization does not work with token:" + ProjectProperties.getPropToken());
            } else if (response.statusCode() != 200) {
                throw new RuntimeException("Something went wrong while clearing data" + response.then().log().all());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteHttp(String endpoint) {
        try {
            if (!listId.isEmpty()) {
                for (String id : listId) {
                    Response response = given()
                            .headers(HttpHeaders.AUTHORIZATION, "Bearer " + Token)
                            .pathParam("endpoint", endpoint)
                            .pathParam("id", id)
                            .when()
                            .delete("http://localhost:80/api/v1/{endpoint}/{id}");

                    if (response.statusCode() != 204) {
                        throw new RuntimeException("Something went wrong while clearing data" + response.then().log().all());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        listId.clear();
    }

    public static void deleteBills() {
        getHttp("bills");
        deleteHttp("bills");
    }

    public static void deleteBudgets() {
        getHttp("budgets");
        deleteHttp("budgets");
    }

    public static void deleteAccounts() {
        getHttp("accounts");
        deleteHttp("accounts");
    }

    public static void deletePiggyBanks() {
        getHttp("piggy-banks");
        deleteHttp("piggy-banks");
    }

    public static void clearData() {
        deleteBills();
        deleteBudgets();
        deleteAccounts();
        deletePiggyBanks();
    }
}