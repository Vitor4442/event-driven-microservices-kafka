package dev.faturamento.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.faturamento.model.Pedido;
import dev.faturamento.publisher.represetation.AtualizaçãoStatusPedido;
import dev.faturamento.publisher.represetation.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar (Pedido pedido, String urlNotaFiscal){
        try {
            var represetation = new AtualizaçãoStatusPedido(pedido.codigo(), StatusPedido.FATURADO, urlNotaFiscal);

            String json = objectMapper.writeValueAsString(represetation);

            kafkaTemplate.send(topico, "dados", json);
        } catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
