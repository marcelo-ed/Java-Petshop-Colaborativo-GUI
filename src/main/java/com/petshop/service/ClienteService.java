package com.petshop.service;

import com.petshop.model.Cliente;
import com.petshop.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    
    private final ClienteRepository clienteRepository = new ClienteRepository();
    
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.salvar(cliente);
    }
    
    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }
    
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.buscarPorCpf(cpf);
    }
    
    public void deletar(Long id) {
        clienteRepository.deletar(id);
    }
    
    public Cliente atualizar(Cliente cliente) {
        return clienteRepository.atualizar(cliente);
    }
} 