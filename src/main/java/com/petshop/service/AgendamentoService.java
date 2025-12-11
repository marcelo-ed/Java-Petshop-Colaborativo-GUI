package com.petshop.service;

import com.petshop.model.Agendamento;
import com.petshop.model.Cliente;
import com.petshop.model.Pacote;
import com.petshop.model.Pet;
import com.petshop.model.Servico;
import com.petshop.repository.AgendamentoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;
    
    public AgendamentoService() {
        this.agendamentoRepository = new AgendamentoRepository();
    }
    
    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getPet() == null) {
            throw new IllegalArgumentException("Um agendamento deve estar sempre vinculado a um pet.");
        }
        return agendamentoRepository.salvar(agendamento);
    }
    
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.listarTodos();
    }
    
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.buscarPorId(id);
    }
    
    public void deletar(Long id) {
        agendamentoRepository.deletar(id);
    }
    
    public List<Agendamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.buscarPorPeriodo(inicio, fim);
    }
    
    public List<Agendamento> buscarPorCliente(Long clienteId) {
        return agendamentoRepository.buscarPorCliente(clienteId);
    }
    
    public List<Agendamento> buscarPorCpfCliente(String cpf) {
        return agendamentoRepository.buscarPorCpfCliente(cpf);
    }
    
    public List<Agendamento> buscarPorPet(Long petId) {
        return agendamentoRepository.buscarPorPet(petId);
    }
    
    public Agendamento agendarServico(Agendamento agendamento) {
        if (agendamento.getPet() == null) {
            throw new IllegalArgumentException("Um agendamento deve estar sempre vinculado a um pet.");
        }
        agendamento.setStatus("AGENDADO");
        return salvar(agendamento);
    }
    
    public Agendamento agendarPacote(Cliente cliente, Pet pet, Pacote pacote, LocalDateTime dataHora) {
        if (pet == null) {
            throw new IllegalArgumentException("Um agendamento deve estar sempre vinculado a um pet.");
        }
        
        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setPet(pet);
        agendamento.setPacote(pacote);
        agendamento.setDataHora(dataHora);
        agendamento.setStatus("AGENDADO");
        
        // Calcula o valor final com desconto do pacote
        BigDecimal valorTotal = pacote.getPrecoTotal();
        BigDecimal desconto = valorTotal.multiply(pacote.getDesconto().divide(new BigDecimal("100")));
        agendamento.setValorFinal(valorTotal.subtract(desconto));
        
        return salvar(agendamento);
    }
    
    public Agendamento cancelarAgendamento(Long id) {
        Optional<Agendamento> agendamentoOpt = buscarPorId(id);
        if (agendamentoOpt.isPresent()) {
            Agendamento agendamento = agendamentoOpt.get();
            agendamento.setStatus("CANCELADO");
            return agendamentoRepository.atualizar(agendamento);
        }
        return null;
    }
    
    private BigDecimal calcularValorComDesconto(Pacote pacote) {
        BigDecimal valorTotal = pacote.getPrecoTotal();
        BigDecimal desconto = valorTotal.multiply(pacote.getDesconto().divide(new BigDecimal("100")));
        return valorTotal.subtract(desconto);
    }
    
    public List<Agendamento> buscarAgendamentosPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.buscarPorPeriodo(inicio, fim);
    }
    
    public List<Agendamento> buscarAgendamentosPorCliente(Long clienteId) {
        return agendamentoRepository.buscarPorCliente(clienteId);
    }
    
    public List<Agendamento> buscarAgendamentosPorPet(Long petId) {
        return agendamentoRepository.buscarPorPet(petId);
    }
} 