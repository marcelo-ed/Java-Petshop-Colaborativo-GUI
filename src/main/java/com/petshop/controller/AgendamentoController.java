package com.petshop.controller;

import com.petshop.model.Agendamento;
import com.petshop.service.AgendamentoService;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoController {
    
    private final AgendamentoService agendamentoService;
    
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }
    
    public Agendamento agendar(Agendamento agendamento) {
        return agendamentoService.agendarServico(agendamento);
    }
    
    public List<Agendamento> listarTodos() {
        return agendamentoService.buscarAgendamentosPorPeriodo(
            LocalDateTime.now().minusDays(30), 
            LocalDateTime.now().plusDays(30)
        );
    }
    
    public Agendamento buscarPorId(Long id) {
        return agendamentoService.buscarPorId(id).orElse(null);
    }
    
    public List<Agendamento> buscarPorCliente(Long clienteId) {
        return agendamentoService.buscarAgendamentosPorCliente(clienteId);
    }
    
    public List<Agendamento> buscarPorPet(Long petId) {
        return agendamentoService.buscarAgendamentosPorPet(petId);
    }
    
    public List<Agendamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoService.buscarAgendamentosPorPeriodo(inicio, fim);
    }
    
    public void cancelar(Long id) {
        agendamentoService.cancelarAgendamento(id);
    }
} 