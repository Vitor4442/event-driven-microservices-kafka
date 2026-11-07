package stockvision.logistc.subscriber.representation;

import stockvision.logistc.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo, StatusPedido status, String urlNotaFiscal
) {
}
