package serverest.userFlowTest;

import org.apache.http.HttpStatus;
import org.junit.Test;
import serverest.control.Carrinho;
import serverest.control.Produto;
import serverest.control.Usuario;
import serverest.model.ProdutoDTO;
import serverest.model.UsuarioDTO;
import serverest.util.Mensagem;

public class Exercicio04 {

/*
REST Assured - Exercício 04
A. Validar que um usuário que possui carrinho associado não pode ser excluído. linha 38
B. Validar que o usuário anterior pode ser excluído após a exclusão do carrinho a ele associado.
Observação: utilizar o endopoint /carrinhos/concluir-compra

app/src/test/java/serverest/control/Produto.java          -> * inclusao do metodo excluirProduto
app/src/test/java/serverest/control/Carrinho.java         -> * inclusao da rota usuarioId e concluirCompra
app/src/test/java/serverest/control/Usuario.java          -> * alteracao de retorno do metodo cadastrar usuario, para
                                                               utilizar o campo incluido em usuarioDTO
app/src/test/java/serverest/model/ProdutoDTO.java         -> * inclusao de novo campo idProduto
app/src/test/java/serverest/model/UsuarioDTO.java         -> * inclusao de novo campo idusuario
app/src/test/java/serverest/userFlowTest/Exercicio03.java -> * alteracao para inclusao de metodos get
                                                               para pegar o idProduto
app/src/test/java/serverest/userFlowTest/Exercicio04.java -> * arquivo novo com exericio proposto
app/src/test/java/serverest/util/Endpoint.java            -> * inclusao de final String usuariosId e concluir compra
app/src/test/java/serverest/util/Mensagem.java            -> * inclusao mensagem de exclusao e erro de negocio.

*/
    @Test
    public void concluirCompra() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("true");
        //Cadastrar um usuário
        usuarioDTO = Usuario.cadastrarUsuario(usuarioDTO, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Autenticar usuário
        String userToken = Usuario.autenticarUsuario(usuarioDTO, HttpStatus.SC_OK, Mensagem.loginSucesso);
        //Cadstrar um produto
        ProdutoDTO produtoDTO = Produto.gerarPokemon(100, 100);
        produtoDTO = Produto.cadastrarProduto(produtoDTO, userToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Verificar estoque do produto
        Produto.verificarEstoque(produtoDTO.getIdProduto(), 100);
        //Cadastrar carrinho
        Carrinho.cadastrarCarrinho(produtoDTO.getIdProduto(), 1, userToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Verificar estoque do produto
        Produto.verificarEstoque(produtoDTO.getIdProduto(), 99);
        // verificar quer usuario com carrinho associado nao pode ser excluido
        Usuario.excluirUsuario(usuarioDTO, HttpStatus.SC_BAD_REQUEST, Mensagem.exclusaoUsuarioComCarrinhoFalha);
        //concluir a compra
        Carrinho.concluirCompra(userToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
        //Verificar estoque do produto, apos a conclusao da compra, ele deve deve te diminuido
        Produto.verificarEstoque(produtoDTO.getIdProduto(), 99);
        // exclusao da massa ja utilizada (produto)
        Produto.excluirProduto(produtoDTO, userToken, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);
        // exclusao da massa ja utilizada (usuario)
        Usuario.excluirUsuario(usuarioDTO, HttpStatus.SC_OK, Mensagem.exclusaoSucesso);

    }
}