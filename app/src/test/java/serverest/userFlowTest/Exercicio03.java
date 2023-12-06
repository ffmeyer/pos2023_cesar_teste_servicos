package serverest.userFlowTest;

import org.apache.http.HttpStatus;
import org.junit.Test;
import serverest.control.Carrinho;
import serverest.control.Produto;
import serverest.control.Usuario;
import serverest.model.ProdutoDTO;
import serverest.model.UsuarioDTO;
import serverest.util.Mensagem;

public class Exercicio03 {

    @Test
    public void validarEstoque() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("true");
        //Cadastrar um usu?rio
        String userId = Usuario.cadastrarUsuario(usuarioDTO, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Autenticar usu?rio
        String userToken = Usuario.autenticarUsuario(usuarioDTO, HttpStatus.SC_OK, Mensagem.loginSucesso);
        //Cadstrar um produto
        ProdutoDTO produtoDTO = Produto.gerarPokemon(100, 100);
        String productId = Produto.cadastrarProduto(produtoDTO, userToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Verificar estoque do produto
        Produto.verificarEstoque(productId, 100);
        //Cadastrar carrinho
        Carrinho.cadastrarCarrinho(productId, 1, userToken, HttpStatus.SC_CREATED, Mensagem.cadastroSucesso);
        //Verificar estoque do produto
        Produto.verificarEstoque(productId, 99);
        //Cancelar a compra
        Carrinho.cancelarCompra(userToken, HttpStatus.SC_OK, Mensagem.deleteProdutoSucesso);
        //Verificar estoque do produto
        Produto.verificarEstoque(productId, 100);
    }
}