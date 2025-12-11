package com.petshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pacote {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal desconto;
    private List<Servico> servicos;
    private BigDecimal precoTotal;
    private String servicoIds;

    public String getServicoIds() {
        return servicoIds;
    }

    public void setServicoIds(String servicoIds) {
        this.servicoIds = servicoIds;
    }


    public Pacote() {
        this.servicos = new ArrayList<>();
        this.precoTotal = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        calcularPrecoTotal();
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
        calcularPrecoTotal();
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public void adicionarServico(Servico servico) {
        this.servicos.add(servico);
        calcularPrecoTotal();
    }

    public void removerServico(Servico servico) {
        this.servicos.remove(servico);
        calcularPrecoTotal();
    }

    private void calcularPrecoTotal() {
        this.precoTotal = servicos.stream()
                .map(Servico::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getPrecoComDesconto() {
        if (desconto == null || desconto.compareTo(BigDecimal.ZERO) == 0) {
            return precoTotal;
        }
        return precoTotal.subtract(precoTotal.multiply(desconto.divide(new BigDecimal("100"))));
    }
} 