package com.petshop.service;

import com.petshop.model.Pacote;
import com.petshop.repository.PacoteRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PacoteService {
    
    private final PacoteRepository pacoteRepository = new PacoteRepository();
    
    public Pacote criarPacote(Pacote pacote) {
        pacote.setPrecoTotal(calcularPrecoTotal(pacote));
        return pacoteRepository.salvar(pacote);
    }
    
    private BigDecimal calcularPrecoTotal(Pacote pacote) {
        return pacote.getServicos().stream()
                .map(servico -> servico.getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<Pacote> listarTodos() {
        return pacoteRepository.listarTodos();
    }
    
    public Optional<Pacote> buscarPorId(Long id) {
        return pacoteRepository.buscarPorId(id);
    }
    
    public void deletar(Long id) {
        pacoteRepository.deletar(id);
    }
    
    public Pacote atualizar(Pacote pacote) {
        pacote.setPrecoTotal(calcularPrecoTotal(pacote));
        return pacoteRepository.atualizar(pacote);
    }
} 