package io.github.vitor4442.icompras.pedidos.dto;

import io.github.vitor4442.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento){
}
