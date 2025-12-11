package com.petshop.controller;

import com.petshop.model.Cliente;
import com.petshop.service.ClienteService;
import java.util.List;

public class ClienteController {
    
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    public Cliente criar(Cliente cliente) {
        return clienteService.salvar(cliente);
    }
    
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }
    
    public Cliente buscarPorId(Long id) {
        return clienteService.buscarPorId(id).orElse(null);
    }
    
    public Cliente buscarPorCpf(String cpf) {
        return clienteService.buscarPorCpf(cpf).orElse(null);
    }
    
    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        return clienteService.atualizar(cliente);
    }
    
    public void deletar(Long id) {
        clienteService.deletar(id);
    }
} 