package io.github.vitor4442.icompras.servicos.model;


import io.github.vitor4442.icompras.servicos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}