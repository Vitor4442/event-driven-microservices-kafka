package io.github.vitor4442.icompras.pedidos.publisher.represetation;


import java.math.BigDecimal;


public record DetalheItemPedidoRepresetation(
        Long codigoProduto,
        String nome,
        Integer quantitdade,
        BigDecimal valorUnitario,
        BigDecimal total
) {
}
