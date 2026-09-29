package dev.faturamento.api;

import dev.faturamento.bucket.BucketFile;
import dev.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try (InputStream is = file.getInputStream()){

            MediaType type = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getName(), is, type, file.getSize());
            service.upload(bucketFile);

            return ResponseEntity.ok("Arquivo enviado com sucesso");

        } catch (Exception e){
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }
}
