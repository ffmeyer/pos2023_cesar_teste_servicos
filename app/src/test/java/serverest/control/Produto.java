package serverest.control;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import serverest.model.ProdutoDTO;
import serverest.util.Endpoint;
import serverest.util.Environment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Produto {

    public static void listarProdutos() {
        when()
                .get(Environment.localhost + Endpoint.produtos)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public static ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO, String userToken, Integer statusCode, String message) {
         String idproduto = given()
                .header("authorization", userToken)
                .body("{\n" +
                        "  \"nome\": \"" + produtoDTO.getNome() + "\",\n" +
                        "  \"preco\": " + produtoDTO.getPreco() + ",\n" +
                        "  \"descricao\": \"" + produtoDTO.getDescricao() + "\",\n" +
                        "  \"quantidade\": " + produtoDTO.getQuantidade() + "\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post(Environment.localhost + Endpoint.produtos)
        .then()
                .statusCode(statusCode)
                .body("message", is(message))
                .extract().path("_id");
        produtoDTO.setIdProduto(idproduto);
        return produtoDTO;
    }

    public static void verificarEstoque(String productId, Integer quantidadeEsperada) {
        given()
                .pathParam("_id", productId)
        .when()
                .get(Environment.localhost + Endpoint.produtosId)
        .then()
                .body("quantidade", is(quantidadeEsperada));
    }

    public static ProdutoDTO gerarPokemon(Integer preco, Integer quantidade) {
        Faker faker = new Faker();
        String nome = faker.pokemon().name();
        String descricao = "Figure pokemon " + nome;
        ProdutoDTO produtoDTO = new ProdutoDTO(nome, preco, descricao, quantidade);
        return produtoDTO;
    }


    public static void excluirProduto (ProdutoDTO produtoDTO, String userToken, Integer statusCode, String message) {
        given()
                .header("authorization", userToken)
                .pathParam("_id", produtoDTO.getIdProduto())
                .contentType(ContentType.JSON)
        .when()
                .delete(Environment.localhost + Endpoint.produtosId)
        .then()
                .statusCode(statusCode)
                .body("message", is(message))
                .extract().path("message");
    }

}