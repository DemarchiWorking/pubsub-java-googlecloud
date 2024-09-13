package br.edu.infnet.model;

import com.google.type.DateTime;
import org.springframework.integration.annotation.Aggregator;

import java.time.LocalDateTime;


public class Evento {
    private Long pedidoId;
    private String descricao;
    private LocalDateTime data;

    public Evento(Long pedidoId, String descricao, LocalDateTime data) {
        this.pedidoId = pedidoId;
        this.descricao = descricao;
        this.data = data;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
