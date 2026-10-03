package dev.faturamento.mapper;

import dev.faturamento.model.Cliente;
import dev.faturamento.model.ItemPedido;
import dev.faturamento.model.Pedido;

import dev.faturamento.subscriber.represetation.DetalheItemPedidoRepresentation;
import dev.faturamento.subscriber.represetation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation){
        Cliente cliente = new Cliente(
                representation.nome(), representation.cpf(), representation.logradouro(),
                representation.numero(), representation.bairro(), representation.email(),
                representation.telefone()
        );

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();

        return new Pedido(representation.codigo(), cliente,
                representation.dataPedido(), representation.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation){
        return new ItemPedido(representation.codigoProduto(), representation.nome(),
                representation.valorUnitario(), representation.quantidade(),
                representation.total());
    }
}