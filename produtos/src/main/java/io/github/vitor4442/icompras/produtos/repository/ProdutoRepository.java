package io.github.vitor4442.icompras.produtos.repository;

import io.github.vitor4442.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
