package com.petshop.gui.cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.petshop.model.Cliente;
import com.petshop.service.ClienteService;

public class TelaListarCli extends JFrame {

    private static final long serialVersionUID = 1L;

    private final ClienteService clienteService;
    private JTable table;
    private JTextField txtPesquisar;

    public TelaListarCli(ClienteService clienteService) {
        this.clienteService = clienteService;

        setTitle("Lista de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 350);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Pesquisar (nome):"));

        txtPesquisar = new JTextField(15);
        aplicarPlaceholder();
        panelTop.add(txtPesquisar);

        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnAtualizar = new JButton("Atualizar");

        panelTop.add(btnPesquisar);
        panelTop.add(btnAtualizar);
        contentPane.add(panelTop, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        carregarTabela(clienteService.listarTodos());

        JLabel lblAviso = new JLabel(
            "Clique duas vezes em uma linha para selecionar o cliente."
        );
        lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblAviso, BorderLayout.SOUTH);

        btnPesquisar.addActionListener(e -> pesquisarPorNome());

        btnAtualizar.addActionListener(e ->
            carregarTabela(clienteService.listarTodos())
        );

        configurarDuploClique();
    }

    private void aplicarPlaceholder() {
        txtPesquisar.setText("nome do cliente");
        txtPesquisar.setForeground(Color.GRAY);

        txtPesquisar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtPesquisar.getText().equals("nome do cliente")) {
                    txtPesquisar.setText("");
                    txtPesquisar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtPesquisar.getText().isEmpty()) {
                    txtPesquisar.setText("nome do cliente");
                    txtPesquisar.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void carregarTabela(List<Cliente> clientes) {
        String[] colunas = { "ID", "Nome", "CPF", "Telefone", "E-mail" };

        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Cliente c : clientes) {
            model.addRow(new Object[] {
                c.getId(),
                c.getNome(),
                c.getCpf(),
                c.getTelefone(),
                c.getEmail()
            });
        }

        table.setModel(model);
    }

    private void pesquisarPorNome() {
        String texto = txtPesquisar.getText().trim().toLowerCase();

        if (texto.isEmpty() || texto.equals("nome do cliente")) {
            carregarTabela(clienteService.listarTodos());
            return;
        }

        List<Cliente> resultado = new ArrayList<>();

        for (Cliente c : clienteService.listarTodos()) {
            if (c.getNome() != null &&
                c.getNome().toLowerCase().contains(texto)) {
                resultado.add(c);
            }
        }

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Nenhum cliente encontrado."
            );
        }

        carregarTabela(resultado);
    }

    private void configurarDuploClique() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = table.getSelectedRow();
                    if (linha == -1) return;

                    Long id = (Long) table.getValueAt(linha, 0);

                    clienteService.buscarPorId(id).ifPresent(cliente -> {
                        new TelaEscolhaCli(clienteService, cliente)
                            .setVisible(true);
                    });
                }
            }
        });
    }
}
