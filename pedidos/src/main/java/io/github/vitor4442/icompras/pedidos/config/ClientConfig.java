package io.github.vitor4442.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.github.vitor4442.icompras.pedidos.client")
public class ClientConfig {
}
