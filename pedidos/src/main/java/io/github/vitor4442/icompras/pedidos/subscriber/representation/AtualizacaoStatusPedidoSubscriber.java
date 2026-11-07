package io.github.vitor4442.icompras.pedidos.subscriber.representation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vitor4442.icompras.pedidos.service.AtualizacaoStatusPedidoService;
import io.github.vitor4442.icompras.pedidos.subscriber.AtualizacaoStatusPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoSubscriber {

    private final AtualizacaoStatusPedidoService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
            "${icompras.config.kafka.topics.pedidos-faturados}",
            "${icompras.config.kafka.topics.pedidos-enviados}"
    })
    public void receberAtualizacao(String json){
        log.info("Recebendo atualização de status:  {}", json);

        try {
            var atualizacoStatus = objectMapper.readValue(json, AtualizacaoStatusPedidoRepresentation.class);
            service.atualizarStatus(atualizacoStatus.codigo(), atualizacoStatus.status(), atualizacoStatus.urlNotaFiscal(), atualizacoStatus.codigoRastreio());
            log.info("Pedido atualizado com sucesso!");
        } catch (Exception e){
            log.error("Erro ao atualizar status pedido: {}", e.getMessage());
        }
    }
}
