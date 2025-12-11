package com.petshop.controller;

import com.petshop.model.Servico;
import com.petshop.service.ServicoService;
import java.util.List;

public class ServicoController {
    
    private final ServicoService servicoService;
    
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }
    
    public Servico criar(Servico servico) {
        return servicoService.salvar(servico);
    }
    
    public List<Servico> listarTodos() {
        return servicoService.listarTodos();
    }
    
    public Servico buscarPorId(Long id) {
        return servicoService.buscarPorId(id).orElse(null);
    }
    
    public Servico atualizar(Long id, Servico servico) {
        servico.setId(id);
        return servicoService.atualizar(servico);
    }
    
    public void deletar(Long id) {
        servicoService.deletar(id);
    }
} 