package dev.faturamento;

import dev.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeradorNotaFiscalService {

    public void gerar(Pedido pedido){
        log.info("Gerada a nota fiscal para o pedido {} ", pedido.codigo());
    }
}
