package io.github.vitor4442.icompras.pedidos.service;


import io.github.vitor4442.icompras.pedidos.client.ClientesClient;
import io.github.vitor4442.icompras.pedidos.client.ProdutosClient;
import io.github.vitor4442.icompras.pedidos.client.ServicoBancarioClient;
import io.github.vitor4442.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.vitor4442.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.vitor4442.icompras.pedidos.model.DadosPagamento;
import io.github.vitor4442.icompras.pedidos.model.ItemPedido;
import io.github.vitor4442.icompras.pedidos.model.Pedido;
import io.github.vitor4442.icompras.pedidos.model.enums.StatusPedido;
import io.github.vitor4442.icompras.pedidos.model.enums.TipoPagamento;
import io.github.vitor4442.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.vitor4442.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.vitor4442.icompras.pedidos.repository.PedidoRepository;
import io.github.vitor4442.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClient;
    private final ProdutosClient apiProdutos;

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

    public void atualizarStatusPagamento(Long codigo, String chavePagamento, boolean sucesso, String observacoes) {
        Optional<Pedido> pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigo, chavePagamento);

        if(pedidoEncontrado.isEmpty()){
            throw new ItemNaoEncontradoException("Pedido não encontrado para o código informado");
        }

        Pedido pedido = pedidoEncontrado.get();

        if(sucesso){
            pedido.setStatus(StatusPedido.PAGO);

        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        repository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo){
        Optional<Pedido> pedidoEncontrado = repository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()){
            return;
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o novo processamento.");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        repository.save(pedido);

    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo){
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        ResponseEntity<ClienteRepresentation> response = apiClient.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido){
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item){
        Long codigoProduto = item.getCodigoProduto();
        ResponseEntity<ProdutoRepresentation> response = apiProdutos.obterDados(codigoProduto);
        item.setNome(response.getBody().nome());
    }



}
