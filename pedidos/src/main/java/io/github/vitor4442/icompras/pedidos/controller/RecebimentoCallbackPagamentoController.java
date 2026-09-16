package io.github.vitor4442.icompras.pedidos.controller;

import io.github.vitor4442.icompras.pedidos.dto.RecebimentoCallbackPagamentoDTO;
import io.github.vitor4442.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    public ResponseEntity<Object> atualizarStatusPagamento(@RequestBody RecebimentoCallbackPagamentoDTO body, @RequestHeader(required = true, name = "apiKey") String apiKey) {
        pedidoService.atualizarStatusPagamento(body.codigo(), body.chavePagamento(), body.status(), body.observacoes());
        return ResponseEntity.ok().build();
    }
}
