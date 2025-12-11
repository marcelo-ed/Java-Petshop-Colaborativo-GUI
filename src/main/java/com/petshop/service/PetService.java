package com.petshop.service;

import com.petshop.model.Pet;
import com.petshop.repository.PetRepository;
import java.util.List;
import java.util.Optional;

public class PetService {
    
    private final PetRepository petRepository = new PetRepository();
    
    public Pet salvar(Pet pet) {
        if (pet.getCliente() == null) {
            throw new IllegalArgumentException("Um pet deve estar sempre vinculado a um cliente.");
        }
        return petRepository.salvar(pet);
    }
    
    public List<Pet> listarTodos() {
        return petRepository.listarTodos();
    }
    
    public Optional<Pet> buscarPorId(Long id) {
        return petRepository.buscarPorId(id);
    }
    
    public void deletar(Long id) {
        petRepository.deletar(id);
    }
    
    public Pet atualizar(Pet pet) {
        return petRepository.atualizar(pet);
    }
    
    public List<Pet> buscarPorCliente(Long clienteId) {
        return petRepository.buscarPorCliente(clienteId);
    }
} 