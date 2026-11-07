package io.github.vitor4442.icompras.pedidos.service;

import io.github.vitor4442.icompras.pedidos.model.enums.StatusPedido;
import io.github.vitor4442.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String rastreio){

    }
}
