package dev.faturamento.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProps {

    private String url;
    private String acessKey;
    private String secretKey;
    private String bucketName;
}
