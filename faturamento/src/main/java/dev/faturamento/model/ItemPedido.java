package dev.faturamento.model;

import java.math.BigDecimal;

public record ItemPedido(
        Long codigo,
        String descrição,
        BigDecimal valorUnitario,
        Integer quantidade,
        BigDecimal total) {
    public BigDecimal getTotal(){
        return BigDecimal.valueOf(this.quantidade).multiply(this.valorUnitario);
    }
}
