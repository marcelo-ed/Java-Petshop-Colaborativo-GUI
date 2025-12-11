package com.petshop.controller;

import com.petshop.model.Pacote;
import com.petshop.service.PacoteService;
import java.util.List;

public class PacoteController {
    
    private final PacoteService pacoteService;
    
    public PacoteController(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }
    
    public Pacote criar(Pacote pacote) {
        return pacoteService.criarPacote(pacote);
    }
    
    public List<Pacote> listarTodos() {
        return pacoteService.listarTodos();
    }
    
    public Pacote buscarPorId(Long id) {
        return pacoteService.buscarPorId(id).orElse(null);
    }
    
    public Pacote atualizar(Long id, Pacote pacote) {
        pacote.setId(id);
        return pacoteService.atualizar(pacote);
    }
    
    public void deletar(Long id) {
        pacoteService.deletar(id);
    }
} 