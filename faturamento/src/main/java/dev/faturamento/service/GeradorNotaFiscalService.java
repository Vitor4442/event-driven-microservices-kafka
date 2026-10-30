package dev.faturamento.service;

import dev.faturamento.bucket.BucketFile;
import dev.faturamento.bucket.BucketService;
import dev.faturamento.model.Pedido;
import dev.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;
    private final FaturamentoPublisher publisher;

    public void gerar(Pedido pedido){
        log.info("Gerada a nota fiscal para o pedido {} ", pedido.codigo());

        try {
            byte[] byteArray = notaFiscalService.gerarNota(pedido);

            String nomeArquivo = String.format("notafiscal_pedido_%d.pdf", pedido.codigo());

            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);

            bucketService.upload(file);

            String url = bucketService.getUrl(nomeArquivo);
            publisher.publicar(pedido, url);

        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
