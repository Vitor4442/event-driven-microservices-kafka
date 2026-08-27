package io.github.vitor4442.icompras.pedidos.validator;

import io.github.vitor4442.icompras.pedidos.client.ProdutosClient;
import io.github.vitor4442.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.vitor4442.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;

    public void validar(Pedido pedido){

    }
}
