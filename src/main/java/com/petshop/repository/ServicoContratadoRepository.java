package com.petshop.repository;

import com.petshop.model.ServicoContratado;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoContratadoRepository {

    private static final String CAMINHO_ARQUIVO = "data/servicos_contratados.csv";

    public ServicoContratadoRepository() {
        criarArquivoSeNaoExistir();
    }

    public ServicoContratado salvar(ServicoContratado sc) {
        List<ServicoContratado> lista = listarTodos();

        long novoId = lista.stream()
                .mapToLong(ServicoContratado::getId)
                .max()
                .orElse(0L) + 1;

        sc.setId(novoId);
        lista.add(sc);
        escreverArquivo(lista);

        return sc;
    }

    public List<ServicoContratado> listarTodos() {
        List<ServicoContratado> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] dados = linha.split(";");
                ServicoContratado sc = new ServicoContratado();
                sc.setId(Long.parseLong(dados[0]));
                sc.setPetId(Long.parseLong(dados[1]));
                sc.setServicoId(Long.parseLong(dados[2]));
                sc.setDataContratacao(LocalDate.parse(dados[3]));

                lista.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Optional<ServicoContratado> buscarPorId(Long id) {
        return listarTodos().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public boolean deletar(Long id) {
        List<ServicoContratado> lista = listarTodos();
        boolean removido = lista.removeIf(s -> s.getId().equals(id));
        if (removido) {
            escreverArquivo(lista);
        }
        return removido;
    }

    private void escreverArquivo(List<ServicoContratado> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (ServicoContratado sc : lista) {
                bw.write(
                        sc.getId() + ";" +
                        sc.getPetId() + ";" +
                        sc.getServicoId() + ";" +
                        sc.getDataContratacao()
                );
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void criarArquivoSeNaoExistir() {
        try {
            File arquivo = new File(CAMINHO_ARQUIVO);
            arquivo.getParentFile().mkdirs();
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ServicoContratado> buscarPorPet(Long petId) {
        List<ServicoContratado> todos = listarTodos(); 
        List<ServicoContratado> filtrados = new ArrayList<>();
        for (ServicoContratado sc : todos) {
            if (sc.getPetId().equals(petId)) {
                filtrados.add(sc);
            }
        }
        return filtrados;
    }

}
