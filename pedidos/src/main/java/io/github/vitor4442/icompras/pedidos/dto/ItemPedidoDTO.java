package io.github.vitor4442.icompras.pedidos.dto;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        Long codigoProduto, Integer quantidade, BigDecimal valorUnitario) {
}
