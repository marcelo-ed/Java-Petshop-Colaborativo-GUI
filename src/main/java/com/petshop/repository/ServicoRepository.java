package com.petshop.repository;

import com.petshop.model.Servico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoRepository {

    private static final String CAMINHO_ARQUIVO = "data/servicos.csv";

    public ServicoRepository() {
        criarArquivoSeNaoExistir();
    }

    public Servico salvar(Servico servico) {
        List<Servico> servicos = listarTodos();

        long novoId = servicos.stream()
                .mapToLong(Servico::getId)
                .max()
                .orElse(0L) + 1;

        servico.setId(novoId);
        servicos.add(servico);
        escreverArquivo(servicos);

        return servico;
    }

    public List<Servico> listarTodos() {
        List<Servico> servicos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] dados = linha.split(";");

                Servico servico = new Servico();
                servico.setId(Long.parseLong(dados[0]));
                servico.setNome(dados[1]);
                servico.setDescricao(dados[2]);
                servico.setPreco(new BigDecimal(dados[3]));
                servico.setDuracaoMinutos(Integer.parseInt(dados[4]));

                servicos.add(servico);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return servicos;
    }

    public Optional<Servico> buscarPorId(Long id) {
        return listarTodos().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public boolean deletar(Long id) {
        List<Servico> servicos = listarTodos();
        boolean removido = servicos.removeIf(s -> s.getId().equals(id));

        if (removido) {
            escreverArquivo(servicos);
        }

        return removido;
    }

    public Servico atualizar(Servico servicoAtualizado) {
        List<Servico> servicos = listarTodos();

        for (int i = 0; i < servicos.size(); i++) {
            if (servicos.get(i).getId().equals(servicoAtualizado.getId())) {
                servicos.set(i, servicoAtualizado);
                escreverArquivo(servicos);
                return servicoAtualizado;
            }
        }

        throw new RuntimeException("Serviço não encontrado para atualização");
    }


    private void escreverArquivo(List<Servico> servicos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {

            for (Servico s : servicos) {
                bw.write(
                        s.getId() + ";" +
                        s.getNome() + ";" +
                        s.getDescricao() + ";" +
                        s.getPreco() + ";" +
                        s.getDuracaoMinutos()
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
}
