package io.github.vitor4442.icompras.servicos.repository;

import io.github.vitor4442.icompras.servicos.model.ItemPedido;
import io.github.vitor4442.icompras.servicos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
