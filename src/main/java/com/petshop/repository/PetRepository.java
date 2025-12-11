package com.petshop.repository;

import com.petshop.model.Pet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetRepository {

    private static final Path PATH = Paths.get("data/pets.csv");

    public PetRepository() {
        try {
            if (!Files.exists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.createFile(PATH);
                try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
                    writer.write("id;nome;especie;raca;idade;peso;clienteId");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pets.csv", e);
        }
    }

    public Pet salvar(Pet pet) {
        List<Pet> pets = listarTodos();

        long novoId = pets.stream()
                .mapToLong(p -> p.getId() == null ? 0L : p.getId())
                .max()
                .orElse(0L) + 1;

        pet.setId(novoId);
        pets.add(pet);
        salvarTodos(pets);

        return pet;
    }

    public List<Pet> listarTodos() {
        List<Pet> pets = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(PATH)) {
            String linha = reader.readLine(); // pula cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] dados = linha.split(";", -1); // manter campos vazios

                Pet pet = new Pet();

                // id
                if (dados.length > 0 && !dados[0].isBlank() && !dados[0].equalsIgnoreCase("null")) {
                    try {
                        pet.setId(Long.parseLong(dados[0]));
                    } catch (NumberFormatException ex) {
                        pet.setId(null);
                    }
                } else {
                    pet.setId(null);
                }

                // nome
                pet.setNome(getSafe(dados, 1));

                // especie
                pet.setEspecie(getSafe(dados, 2));

                // raca
                pet.setRaca(getSafe(dados, 3));

                // idade (int)
                if (dados.length > 4 && !dados[4].isBlank() && !dados[4].equalsIgnoreCase("null")) {
                    try {
                        pet.setIdade(Integer.parseInt(dados[4]));
                    } catch (NumberFormatException ex) {
                        pet.setIdade(0);
                    }
                } else {
                    pet.setIdade(0);
                }

                // peso (string)
                pet.setPeso(getSafe(dados, 5));

                // clienteId
                if (dados.length > 6 && !dados[6].isBlank() && !dados[6].equalsIgnoreCase("null")) {
                    try {
                        pet.setClienteId(Long.parseLong(dados[6]));
                    } catch (NumberFormatException ex) {
                        pet.setClienteId(null);
                    }
                } else {
                    pet.setClienteId(null);
                }

                pets.add(pet);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler pets.csv", e);
        }

        return pets;
    }

    public Optional<Pet> buscarPorId(Long id) {
        if (id == null) return Optional.empty();
        return listarTodos().stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst();
    }

    public void deletar(Long id) {
        List<Pet> pets = listarTodos();
        pets.removeIf(p -> p.getId() != null && p.getId().equals(id));
        salvarTodos(pets);
    }

    public Pet atualizar(Pet pet) {
        List<Pet> pets = listarTodos();

        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() != null && pets.get(i).getId().equals(pet.getId())) {
                pets.set(i, pet);
                salvarTodos(pets);
                return pet;
            }
        }

        return salvar(pet);
    }

    public List<Pet> buscarPorCliente(Long clienteId) {
        List<Pet> resultado = new ArrayList<>();
        if (clienteId == null) return resultado;

        for (Pet pet : listarTodos()) {
            if (pet.getClienteId() != null && pet.getClienteId().equals(clienteId)) {
                resultado.add(pet);
            }
        }
        return resultado;
    }

    private void salvarTodos(List<Pet> pets) {
        try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
            writer.write("id;nome;especie;raca;idade;peso;clienteId");
            writer.newLine();

            for (Pet pet : pets) {
                String clienteIdStr = pet.getClienteId() == null ? "" : String.valueOf(pet.getClienteId());
                String idStr = pet.getId() == null ? "" : String.valueOf(pet.getId());
                writer.write(
                    idStr + ";" +
                    safeEmpty(pet.getNome()) + ";" +
                    safeEmpty(pet.getEspecie()) + ";" +
                    safeEmpty(pet.getRaca()) + ";" +
                    pet.getIdade() + ";" +
                    safeEmpty(pet.getPeso()) + ";" +
                    clienteIdStr
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar pets.csv", e);
        }
    }

    private static String getSafe(String[] arr, int idx) {
        if (arr == null || idx >= arr.length) return "";
        String s = arr[idx];
        if (s == null || s.isBlank() || s.equalsIgnoreCase("null")) return "";
        return s;
    }

    private static String safeEmpty(String s) {
        return s == null ? "" : s;
    }
}
