package io.github.vitor4442.icompras.pedidos.service;


import io.github.vitor4442.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.vitor4442.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;

    
}
