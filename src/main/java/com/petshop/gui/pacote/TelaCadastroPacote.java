package com.petshop.gui.pacote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.petshop.model.Pacote;
import com.petshop.model.Servico;
import com.petshop.service.PacoteService;
import com.petshop.service.ServicoService;

public class TelaCadastroPacote extends JFrame {

    private static final long serialVersionUID = 1L;

    private final PacoteService pacoteService;
    private final ServicoService servicoService;

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtDesconto;
    private DefaultListModel<Servico> modeloDisponiveis;
    private DefaultListModel<Servico> modeloSelecionados;
    private JList<Servico> listaDisponiveis;
    private JList<Servico> listaSelecionados;
    private JLabel lblPrecoTotal;
    private JLabel lblPrecoComDesconto;

    public TelaCadastroPacote(PacoteService pacoteService, ServicoService servicoService) {
        this.pacoteService = pacoteService;
        this.servicoService = servicoService;

        setTitle("Cadastrar Pacote");
        setBounds(100, 100, 800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

        form.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        form.add(txtNome);

        form.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        form.add(txtDescricao);

        form.add(new JLabel("Desconto (%):"));
        txtDesconto = new JTextField("0");
        form.add(txtDesconto);

        contentPane.add(form, BorderLayout.NORTH);

        modeloDisponiveis = new DefaultListModel<>();
        modeloSelecionados = new DefaultListModel<>();

        listaDisponiveis = new JList<>(modeloDisponiveis);
        listaDisponiveis.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaSelecionados = new JList<>(modeloSelecionados);
        listaSelecionados.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JPanel center = new JPanel(new BorderLayout(6, 6));
        JPanel listsPanel = new JPanel(new GridLayout(1, 3, 6, 6));

        JPanel pDisponiveis = new JPanel(new BorderLayout(4, 4));
        pDisponiveis.add(new JLabel("Serviços disponíveis"), BorderLayout.NORTH);
        pDisponiveis.add(new JScrollPane(listaDisponiveis), BorderLayout.CENTER);

        JPanel pBotoes = new JPanel(new GridLayout(5, 1, 4, 4));
        JButton btnAdd = new JButton(">>");
        JButton btnAddAll = new JButton(">> Todos");
        JButton btnRemove = new JButton("<<");
        JButton btnRemoveAll = new JButton("<< Todos");
        pBotoes.add(btnAdd);
        pBotoes.add(btnAddAll);
        pBotoes.add(btnRemove);
        pBotoes.add(btnRemoveAll);

        JPanel pSelecionados = new JPanel(new BorderLayout(4, 4));
        pSelecionados.add(new JLabel("Serviços no pacote"), BorderLayout.NORTH);
        pSelecionados.add(new JScrollPane(listaSelecionados), BorderLayout.CENTER);

        listsPanel.add(pDisponiveis);
        listsPanel.add(pBotoes);
        listsPanel.add(pSelecionados);

        center.add(listsPanel, BorderLayout.CENTER);

        JPanel totalsPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        lblPrecoTotal = new JLabel("Preço total: R$ 0.00");
        lblPrecoComDesconto = new JLabel("Preço com desconto: R$ 0.00");
        totalsPanel.add(lblPrecoTotal);
        totalsPanel.add(lblPrecoComDesconto);

        center.add(totalsPanel, BorderLayout.SOUTH);

        contentPane.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        bottom.add(btnCancelar);
        bottom.add(btnSalvar);

        contentPane.add(bottom, BorderLayout.SOUTH);

        carregarServicosDisponiveis();

        btnAdd.addActionListener(e -> {
            List<Servico> selecionados = listaDisponiveis.getSelectedValuesList();
            for (Servico s : selecionados) {
                if (!modeloSelecionados.contains(s)) {
                    modeloSelecionados.addElement(s);
                    modeloDisponiveis.removeElement(s);
                }
            }
            atualizarTotais();
        });

        btnAddAll.addActionListener(e -> {
            List<Servico> todos = new ArrayList<>();
            for (int i = 0; i < modeloDisponiveis.size(); i++) {
                todos.add(modeloDisponiveis.get(i));
            }
            for (Servico s : todos) {
                if (!modeloSelecionados.contains(s)) modeloSelecionados.addElement(s);
                modeloDisponiveis.removeElement(s);
            }
            atualizarTotais();
        });

        btnRemove.addActionListener(e -> {
            List<Servico> removidos = listaSelecionados.getSelectedValuesList();
            for (Servico s : removidos) {
                if (!modeloDisponiveis.contains(s)) modeloDisponiveis.addElement(s);
                modeloSelecionados.removeElement(s);
            }
            atualizarTotais();
        });

        btnRemoveAll.addActionListener(e -> {
            List<Servico> todos = new ArrayList<>();
            for (int i = 0; i < modeloSelecionados.size(); i++) {
                todos.add(modeloSelecionados.get(i));
            }
            for (Servico s : todos) {
                if (!modeloDisponiveis.contains(s)) modeloDisponiveis.addElement(s);
                modeloSelecionados.removeElement(s);
            }
            atualizarTotais();
        });

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                BigDecimal desconto = parseDesconto(txtDesconto.getText().trim());

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome do pacote é obrigatório.");
                    return;
                }

                List<Servico> servicosNoPacote = new ArrayList<>();
                for (int i = 0; i < modeloSelecionados.size(); i++) {
                    servicosNoPacote.add(modeloSelecionados.get(i));
                }
                if (servicosNoPacote.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Adicione pelo menos um serviço ao pacote.");
                    return;
                }

                Pacote pacote = new Pacote();
                pacote.setNome(nome);
                pacote.setDescricao(descricao);
                pacote.setDesconto(desconto);
                pacote.setServicos(servicosNoPacote);

                String servicoIds = servicosNoPacote.stream()
                        .map(s -> String.valueOf(s.getId()))
                        .collect(Collectors.joining(","));
                pacote.setServicoIds(servicoIds);

                pacoteService.criarPacote(pacote);

                JOptionPane.showMessageDialog(this, "Pacote cadastrado com sucesso.");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Desconto inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar pacote: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        txtDesconto.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { atualizarTotais(); }
            @Override public void removeUpdate(DocumentEvent e) { atualizarTotais(); }
            @Override public void changedUpdate(DocumentEvent e) { atualizarTotais(); }
        });

        atualizarTotais();
    }

    private void carregarServicosDisponiveis() {
        modeloDisponiveis.clear();
        List<Servico> servicos = servicoService.listarTodos();
        for (Servico s : servicos) {
            modeloDisponiveis.addElement(s);
        }
    }

    private BigDecimal parseDesconto(String texto) {
        if (texto == null || texto.isBlank()) return BigDecimal.ZERO;
        return new BigDecimal(texto);
    }

    private void atualizarTotais() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modeloSelecionados.size(); i++) {
            Servico s = modeloSelecionados.get(i);
            if (s.getPreco() != null) total = total.add(s.getPreco());
        }
        BigDecimal desconto = BigDecimal.ZERO;
        try {
            desconto = parseDesconto(txtDesconto.getText().trim());
        } catch (Exception e) {
            desconto = BigDecimal.ZERO;
        }
        BigDecimal precoComDesconto = total;
        if (desconto.compareTo(BigDecimal.ZERO) > 0) {
            precoComDesconto = total.subtract(total.multiply(desconto.divide(new BigDecimal("100"))));
        }
        lblPrecoTotal.setText("Preço total: R$ " + total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        lblPrecoComDesconto.setText("Preço com desconto: R$ " + precoComDesconto.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
}
