package io.github.vitor4442.icompras.pedidos.dto;

import io.github.vitor4442.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}