package com.petshop.gui.cliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.petshop.model.Cliente;
import com.petshop.service.ClienteService;

public class TelaEditarCli extends JFrame {

    private static final long serialVersionUID = 1L;
    private final ClienteService clienteService;
    private final Cliente cliente;

    private JTextField nome;
    private JTextField telefone;
    private JTextField email;

    public TelaEditarCli(ClienteService clienteService, Cliente cliente) {
        this.clienteService = clienteService;
        this.cliente = cliente;

        setTitle("Editar Cliente");
        setBounds(100, 100, 350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 80, 25);
        contentPane.add(lblNome);

        nome = new JTextField(cliente.getNome());
        nome.setBounds(90, 10, 220, 25);
        contentPane.add(nome);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(10, 50, 80, 25);
        contentPane.add(lblTelefone);

        telefone = new JTextField(cliente.getTelefone());
        telefone.setBounds(90, 50, 220, 25);
        contentPane.add(telefone);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(10, 90, 80, 25);
        contentPane.add(lblEmail);

        email = new JTextField(cliente.getEmail());
        email.setBounds(90, 90, 220, 25);
        contentPane.add(email);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(110, 140, 100, 30);
        btnSalvar.addActionListener(e -> salvar());
        contentPane.add(btnSalvar);
    }

    private void salvar() {
        cliente.setNome(nome.getText());
        cliente.setTelefone(telefone.getText());
        cliente.setEmail(email.getText());

        clienteService.atualizar(cliente);

        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        dispose();
    }
}
