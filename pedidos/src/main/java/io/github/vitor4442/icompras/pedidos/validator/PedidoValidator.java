package io.github.vitor4442.icompras.pedidos.validator;

import feign.FeignException;
import io.github.vitor4442.icompras.pedidos.client.ClientesClient;
import io.github.vitor4442.icompras.pedidos.client.ProdutosClient;
import io.github.vitor4442.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.vitor4442.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.vitor4442.icompras.pedidos.model.ItemPedido;
import io.github.vitor4442.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente){
        try{
            ResponseEntity<ClienteRepresentation> response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente de código {} encontrado: {}", cliente.codigo(), cliente.nome());
        } catch (FeignException.NotFound e) {
            log.error("Cliente  não encontrado");
        }

    }

    private void validarItem(ItemPedido item){
        try{
            ResponseEntity<ProdutoRepresentation> response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Cliente de código {} encontrado: {}", produto.codigo(), produto.nome());
        } catch (FeignException.NotFound e){
            log.error("Produto de código de não encontrado: {}", item.getCodigo());
        }catch (FeignException.MethodNotAllowed e){
            log.error("Produto de código de não encontrado: {}", item.getCodigo());
        }
    }
}
