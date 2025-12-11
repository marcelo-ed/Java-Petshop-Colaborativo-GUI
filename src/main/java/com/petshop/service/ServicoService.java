package com.petshop.service;

import com.petshop.model.Servico;
import com.petshop.repository.ServicoRepository;

import java.util.List;
import java.util.Optional;

public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService() {
        this.servicoRepository = new ServicoRepository();
    }

    public Servico salvar(Servico servico) {
        // regra mínima: não salvar null
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser null");
        }
        return servicoRepository.salvar(servico);
    }

    public List<Servico> listarTodos() {
        return servicoRepository.listarTodos();
    }

    public Optional<Servico> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return servicoRepository.buscarPorId(id);
    }

    public boolean deletar(Long id) {
        if (id == null) {
            return false;
        }
        return servicoRepository.deletar(id);
    }

    public Servico atualizar(Servico servico) {
        if (servico == null || servico.getId() == null) {
            throw new IllegalArgumentException("Serviço ou ID inválido para atualização");
        }
        return servicoRepository.atualizar(servico);
    }
}
