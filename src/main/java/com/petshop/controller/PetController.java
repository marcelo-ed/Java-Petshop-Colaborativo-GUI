package com.petshop.controller;

import com.petshop.model.Pet;
import com.petshop.service.PetService;
import java.util.List;

public class PetController {
    
    private final PetService petService;
    
    public PetController(PetService petService) {
        this.petService = petService;
    }
    
    public Pet criar(Pet pet) {
        return petService.salvar(pet);
    }
    
    public List<Pet> listarTodos() {
        return petService.listarTodos();
    }
    
    public Pet buscarPorId(Long id) {
        return petService.buscarPorId(id).orElse(null);
    }
    
    public Pet atualizar(Long id, Pet pet) {
        pet.setId(id);
        return petService.atualizar(pet);
    }
    
    public void deletar(Long id) {
        petService.deletar(id);
    }
} 