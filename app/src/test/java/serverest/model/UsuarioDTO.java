package serverest.model;

import com.github.javafaker.Faker;

public class UsuarioDTO {

    private String nome;
    private String email;
    private String password;
    private String administrador;

    public UsuarioDTO(String administrador) {
        Faker faker = new Faker();
        this.nome = faker.name().firstName();
        this.email = this.nome + "@faker.com";
        this.password = "teste";
        this.administrador = administrador;
    }

    public UsuarioDTO(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAdministrador() {
        return administrador;
    }
}