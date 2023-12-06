package serverest.control;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import serverest.model.UsuarioDTO;
import serverest.util.Endpoint;
import serverest.util.Environment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Usuario {

    public static void listarUsuarios() {
        when()
                .get(Environment.localhost + Endpoint.usuarios)
        .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public static UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO, Integer statusCode, String message) {
        String idUsuario = given()
                .body("{\n" +
                        "  \"nome\": \"" + usuarioDTO.getNome() + "\",\n" +
                        "  \"email\": \"" + usuarioDTO.getEmail() + "\",\n" +
                        "  \"password\": \"" + usuarioDTO.getPassword() + "\",\n" +
                        "  \"administrador\": \"" + usuarioDTO.getAdministrador() + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post(Environment.localhost + Endpoint.usuarios)
        .then()
                .statusCode(statusCode)
                .body("message", is(message))
                .extract().path("_id");
        usuarioDTO.setIdUsuario(idUsuario);
        return usuarioDTO;
    }

    public static String autenticarUsuario(UsuarioDTO usuarioDTO, Integer statusCode, String mensage) {
        return given()
                .body("{\n" +
                        "  \"email\": \"" + usuarioDTO.getEmail() + "\",\n" +
                        "  \"password\": \"" + usuarioDTO.getPassword() + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post(Environment.localhost + Endpoint.login)
        .then()
                .statusCode(statusCode)
                .body("message", is(mensage))
                .extract().path("authorization");
    }

    public static void  excluirUsuario(UsuarioDTO usuarioDTO, Integer statusCode, String message) {
                given()
                    .pathParam("_id", usuarioDTO.getIdUsuario())
                    .contentType(ContentType.JSON)
                .when()
                    .delete(Environment.localhost + Endpoint.usuariosId)
                .then()
                    .statusCode(statusCode)
                    .body("message", is(message))
                    .extract().path("message");
    }
}