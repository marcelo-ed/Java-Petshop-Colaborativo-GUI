package com.petshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Agendamento {
    private Long id;
    private Cliente cliente;
    private Pet pet;
    private Servico servico;
    private Pacote pacote;
    private LocalDateTime dataHora;
    private String status; // AGENDADO, CONCLUIDO, CANCELADO
    private BigDecimal valorFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        calcularValorFinal();
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
        calcularValorFinal();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void calcularValorFinal() {
        if (servico != null) {
            this.valorFinal = servico.getPreco();
        } else if (pacote != null) {
            this.valorFinal = pacote.getPrecoComDesconto();
        } else {
            this.valorFinal = BigDecimal.ZERO;
        }
    }
} 