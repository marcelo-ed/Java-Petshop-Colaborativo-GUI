package com.petshop.repository;

import com.petshop.model.Pacote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacoteRepository {

    private static final Path PATH = Paths.get("data/pacotes.csv");

    public PacoteRepository() {
        try {
            if (!Files.exists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.createFile(PATH);

                try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
                    writer.write("id;nome;descricao;desconto;servicos");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pacotes.csv", e);
        }
    }

    public Pacote salvar(Pacote pacote) {
        List<Pacote> pacotes = listarTodos();

        long novoId = pacotes.stream()
                .mapToLong(Pacote::getId)
                .max()
                .orElse(0L) + 1;

        pacote.setId(novoId);
        pacotes.add(pacote);
        salvarTodos(pacotes);

        return pacote;
    }

    public List<Pacote> listarTodos() {
        List<Pacote> pacotes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(PATH)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] dados = linha.split(";");

                Pacote pacote = new Pacote();
                pacote.setId(Long.parseLong(dados[0]));
                pacote.setNome(dados[1]);
                pacote.setDescricao(dados[2]);
                pacote.setDesconto(new BigDecimal(dados[3]));

                // serviços salvos como "1,2,3"
                if (dados.length > 4 && !dados[4].isBlank()) {
                    pacote.setServicoIds(dados[4]);
                } else {
                    pacote.setServicoIds("");
                }

                pacotes.add(pacote);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler pacotes.csv", e);
        }

        return pacotes;
    }

    public Optional<Pacote> buscarPorId(Long id) {
        return listarTodos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void deletar(Long id) {
        List<Pacote> pacotes = listarTodos();
        pacotes.removeIf(p -> p.getId().equals(id));
        salvarTodos(pacotes);
    }

    public Pacote atualizar(Pacote pacote) {
        List<Pacote> pacotes = listarTodos();

        for (int i = 0; i < pacotes.size(); i++) {
            if (pacotes.get(i).getId().equals(pacote.getId())) {
                pacotes.set(i, pacote);
                salvarTodos(pacotes);
                return pacote;
            }
        }

        return salvar(pacote);
    }

    private void salvarTodos(List<Pacote> pacotes) {
        try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
            writer.write("id;nome;descricao;desconto;servicos");
            writer.newLine();

            for (Pacote pacote : pacotes) {
                writer.write(
                    pacote.getId() + ";" +
                    pacote.getNome() + ";" +
                    pacote.getDescricao() + ";" +
                    pacote.getDesconto() + ";" +
                    pacote.getServicoIds()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar pacotes.csv", e);
        }
    }
}
