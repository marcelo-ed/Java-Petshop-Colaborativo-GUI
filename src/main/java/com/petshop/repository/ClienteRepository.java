package com.petshop.repository;

import com.petshop.model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    private static final String CAMINHO_ARQUIVO = "data/clientes.csv";

    public ClienteRepository() {
        criarArquivoSeNaoExistir();
    }

    public Cliente salvar(Cliente cliente) {
        List<Cliente> clientes = listarTodos();

        long novoId = clientes.stream()
                .mapToLong(c -> c.getId() == null ? 0L : c.getId())
                .max()
                .orElse(0L) + 1;

        cliente.setId(novoId);
        clientes.add(cliente);
        escreverArquivo(clientes);
        return cliente;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank() || linha.startsWith("id;")) continue;

                String[] f = linha.split(";", -1);

                Cliente c = new Cliente();
                c.setId(Long.parseLong(f[0]));
                c.setNome(f[1].isEmpty() ? null : f[1]);
                c.setCpf(f[2].isEmpty() ? null : f[2]);
                c.setEmail(f[3].isEmpty() ? null : f[3]);
                c.setTelefone(f[4].isEmpty() ? null : f[4]);
                c.setEndereco(f[5].isEmpty() ? null : f[5]);

                lista.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return listarTodos().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return listarTodos().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
                .findFirst();
    }

    public boolean deletar(Long id) {
        List<Cliente> clientes = listarTodos();
        boolean removeu = clientes.removeIf(c -> c.getId().equals(id));
        if (removeu) escreverArquivo(clientes);
        return removeu;
    }

    public Cliente atualizar(Cliente cliente) {
        List<Cliente> clientes = listarTodos();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(cliente.getId())) {
                clientes.set(i, cliente);
                escreverArquivo(clientes);
                return cliente;
            }
        }

        throw new RuntimeException("Cliente não encontrado para atualizar");
    }

    private void escreverArquivo(List<Cliente> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("id;nome;cpf;email;telefone;endereco");
            bw.newLine();

            for (Cliente c : clientes) {
                bw.write(
                        c.getId() + ";" +
                        nz(c.getNome()) + ";" +
                        nz(c.getCpf()) + ";" +
                        nz(c.getEmail()) + ";" +
                        nz(c.getTelefone()) + ";" +
                        nz(c.getEndereco())
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarArquivoSeNaoExistir() {
        try {
            File f = new File(CAMINHO_ARQUIVO);
            File parent = f.getParentFile();
            if (parent != null) parent.mkdirs();

            if (!f.exists()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                    bw.write("id;nome;cpf;email;telefone;endereco");
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nz(String s) {
        return s == null ? "" : s;
    }
}
