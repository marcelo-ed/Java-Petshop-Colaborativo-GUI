package com.petshop.repository;

import com.petshop.model.Agendamento;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class AgendamentoRepository {
    private static final List<Agendamento> agendamentos = new ArrayList<>();
    private static final AtomicLong idCounter = new AtomicLong(1);
    
    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            agendamento.setId(idCounter.getAndIncrement());
        }
        agendamentos.add(agendamento);
        return agendamento;
    }
    
    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos);
    }
    
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getId().equals(id))
                .findFirst();
    }
    
    public void deletar(Long id) {
        agendamentos.removeIf(agendamento -> agendamento.getId().equals(id));
    }
    
    public Agendamento atualizar(Agendamento agendamento) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(agendamento.getId())) {
                agendamentos.set(i, agendamento);
                return agendamento;
            }
        }
        return salvar(agendamento);
    }
    
    public List<Agendamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentos.stream()
                .filter(agendamento -> !agendamento.getDataHora().isBefore(inicio) && !agendamento.getDataHora().isAfter(fim))
                .toList();
    }
    
    public List<Agendamento> buscarPorCliente(Long clienteId) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getCliente() != null && agendamento.getCliente().getId().equals(clienteId))
                .toList();
    }
    
    public List<Agendamento> buscarPorCpfCliente(String cpf) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getCliente() != null && 
                                     agendamento.getCliente().getCpf() != null && 
                                     agendamento.getCliente().getCpf().equals(cpf))
                .toList();
    }
    
    public List<Agendamento> buscarPorPet(Long petId) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getPet() != null && agendamento.getPet().getId().equals(petId))
                .toList();
    }
} 