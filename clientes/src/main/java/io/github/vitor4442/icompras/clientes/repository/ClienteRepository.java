package io.github.vitor4442.icompras.clientes.repository;

import io.github.vitor4442.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

