package io.github.vitor4442.icompras.pedidos.controller;

import io.github.vitor4442.icompras.pedidos.dto.AdicaoNovoPagamentoDTO;
import io.github.vitor4442.icompras.pedidos.dto.NovoPedidoDTO;
import io.github.vitor4442.icompras.pedidos.mapper.PedidoMapper;
import io.github.vitor4442.icompras.pedidos.model.ErroResposta;
import io.github.vitor4442.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.vitor4442.icompras.pedidos.model.exception.ValidationException;
import io.github.vitor4442.icompras.pedidos.publisher.represetation.DetalhePedidoMapper;
import io.github.vitor4442.icompras.pedidos.publisher.represetation.DetalhePedidoRepresentation;
import io.github.vitor4442.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto){
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamento")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO dto){
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e){
            ErroResposta erroResposta = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erroResposta);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetahesPedido(@PathVariable Long codigo){
        return service.carregarDadosCompletosPedido(codigo).map(detalhePedidoMapper::map).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
