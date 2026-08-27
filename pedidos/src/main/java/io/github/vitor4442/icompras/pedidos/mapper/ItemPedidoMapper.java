package io.github.vitor4442.icompras.pedidos.mapper;

import io.github.vitor4442.icompras.pedidos.dto.ItemPedidoDTO;
import io.github.vitor4442.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
