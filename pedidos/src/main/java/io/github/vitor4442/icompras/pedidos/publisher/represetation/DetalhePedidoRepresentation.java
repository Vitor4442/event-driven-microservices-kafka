package io.github.vitor4442.icompras.pedidos.publisher.represetation;

import io.github.vitor4442.icompras.pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoRepresentation(
        Long codigo,
        long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusPedido status,
        List<DetalhePedidoRepresentation> itens
) {
}
