package com.petshop.gui.servico;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.petshop.model.Servico;
import com.petshop.service.ServicoService;

public class TelaListarSer extends JFrame {

    private static final long serialVersionUID = 1L;

    private final ServicoService servicoService;

    private JTable table;
    private JTextField txtPesquisar;
    private JButton btnPesquisar, btnAtualizar, btnExcluir;

    public TelaListarSer(ServicoService servicoService) {
        this.servicoService = servicoService;

        setTitle("Listar Serviços");
        setBounds(100, 100, 700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtPesquisar = new JTextField(20);
        btnPesquisar = new JButton("Pesquisar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        topo.add(txtPesquisar);
        topo.add(btnPesquisar);
        topo.add(btnAtualizar);
        topo.add(btnExcluir);

        add(topo, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        atualizarTabela();

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnPesquisar.addActionListener(e -> pesquisar(txtPesquisar.getText()));
        btnExcluir.addActionListener(e -> excluirServico());
    }

    private void atualizarTabela() {
        String[] colunas = { "ID", "Nome", "Descrição", "Preço", "Duração (min)" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Servico> servicos = servicoService.listarTodos();
        for (Servico s : servicos) {
            Object[] linha = {
                    s.getId(),
                    s.getNome(),
                    s.getDescricao(),
                    s.getPreco(),
                    s.getDuracaoMinutos()
            };
            model.addRow(linha);
        }

        table.setModel(model);
    }

    private void pesquisar(String termo) {
        String[] colunas = { "ID", "Nome", "Descrição", "Preço", "Duração (min)" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Servico> servicos = servicoService.listarTodos();
        for (Servico s : servicos) {
            if (s.getNome().toLowerCase().contains(termo.toLowerCase())) {
                Object[] linha = {
                        s.getId(),
                        s.getNome(),
                        s.getDescricao(),
                        s.getPreco(),
                        s.getDuracaoMinutos()
                };
                model.addRow(linha);
            }
        }

        table.setModel(model);
    }

    private void excluirServico() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Long idServico = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());
                servicoService.deletar(idServico);
                atualizarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir.");
        }
    }
}
