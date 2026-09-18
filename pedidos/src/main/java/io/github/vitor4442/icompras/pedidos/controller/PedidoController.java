package io.github.vitor4442.icompras.pedidos.controller;

import io.github.vitor4442.icompras.pedidos.dto.AdicaoNovoPagamentoDTO;
import io.github.vitor4442.icompras.pedidos.dto.NovoPedidoDTO;
import io.github.vitor4442.icompras.pedidos.mapper.PedidoMapper;
import io.github.vitor4442.icompras.pedidos.model.ErroResposta;
import io.github.vitor4442.icompras.pedidos.model.exception.ValidationException;
import io.github.vitor4442.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

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
        service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
        return ResponseEntity.noContent().build();
    }
}
