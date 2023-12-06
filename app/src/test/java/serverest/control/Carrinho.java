package serverest.control;

import io.restassured.http.ContentType;
import serverest.util.Endpoint;
import serverest.util.Environment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Carrinho {

    public static void cadastrarCarrinho(String productId, Integer quantidade, String userToken, Integer statusCode, String message) {
        given()
                .header("authorization", userToken)
                .body("{\n" +
                        "  \"produtos\": [\n" +
                        "    {\n" +
                        "      \"idProduto\": \"" + productId + "\",\n" +
                        "      \"quantidade\": " + quantidade + "\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post(Environment.localhost + Endpoint.carrinhos)
        .then()
                .statusCode(statusCode)
                .body("message", is(message));
    }

    public static void cancelarCompra(String userToken, Integer statusCode, String message) {
        given()
                .header("authorization", userToken)
        .when()
                .delete(Environment.localhost + Endpoint.cancelarCompra)
        .then()
                .log().all()
                .statusCode(statusCode)
                .body("message", is(message));
    }
}