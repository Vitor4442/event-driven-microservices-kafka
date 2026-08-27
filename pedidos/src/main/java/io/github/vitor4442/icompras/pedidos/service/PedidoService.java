package io.github.vitor4442.icompras.pedidos.service;


import io.github.vitor4442.icompras.pedidos.client.ServicoBancarioClient;
import io.github.vitor4442.icompras.pedidos.model.Pedido;
import io.github.vitor4442.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.vitor4442.icompras.pedidos.repository.PedidoRepository;
import io.github.vitor4442.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;


    @Transactional
    public Pedido criarPedido(Pedido pedido){
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}
