package io.github.vitor4442.icompras.pedidos.dto;

public record RecebimentoCallbackPagamentoDTO(Long codigo, String chavePagamento, boolean status, String observacoes) {
}
