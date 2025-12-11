package com.petshop.gui.servico;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import com.petshop.model.Servico;
import com.petshop.service.ServicoService;

public class TelaCadastroSer extends JFrame {

    private static final long serialVersionUID = 1L;

    private final ServicoService servicoService;

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtPreco;
    private JTextField txtDuracao;

    public TelaCadastroSer(ServicoService servicoService) {
        this.servicoService = servicoService;

        setTitle("Cadastrar Serviço");
        setBounds(100, 100, 400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 2, 5, 5));

        contentPane.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        contentPane.add(txtNome);

        contentPane.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        contentPane.add(txtDescricao);

        contentPane.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        contentPane.add(txtPreco);

        contentPane.add(new JLabel("Duração (minutos):"));
        txtDuracao = new JTextField();
        contentPane.add(txtDuracao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarServico());
        contentPane.add(btnSalvar);
    }

    private void salvarServico() {
        try {
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            BigDecimal preco = new BigDecimal(txtPreco.getText().trim());
            int duracao = Integer.parseInt(txtDuracao.getText().trim());

            if (nome.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.");
                return;
            }

            Servico servico = new Servico();
            servico.setNome(nome);
            servico.setDescricao(descricao);
            servico.setPreco(preco);
            servico.setDuracaoMinutos(duracao);
            servico.setId(null);

            servicoService.salvar(servico);

            JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso.");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço ou duração inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
