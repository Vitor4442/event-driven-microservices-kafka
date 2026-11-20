package stockvision.logistc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stockvision.logistc.model.AtualizacaoEnvioPedido;
import stockvision.logistc.model.StatusPedido;
import stockvision.logistc.publisher.EnvioPedidoPublisher;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher publisher;

    public void enviar(Long codigoPedido, String urlNotaFiscal){
        var codigoRastreio = gerarCodigoRastreio();
        var atualizacaoRepresentation = new AtualizacaoEnvioPedido(codigoPedido, StatusPedido.ENVIADO, codigoRastreio);
        publisher.enviar(atualizacaoRepresentation);
    }

    private String gerarCodigoRastreio(){
        var random = new Random();

        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2  = (char) ('A' + random.nextInt(26));

        int numeros = 10000000 + random.nextInt(900000);

        return "" + letra1 + letra2 + numeros + "BR";
    }
}
