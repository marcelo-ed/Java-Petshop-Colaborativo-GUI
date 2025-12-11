package com.petshop.gui.pet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.petshop.model.Pet;
import com.petshop.model.Cliente;
import com.petshop.service.PetService;
import com.petshop.repository.ClienteRepository;

import java.awt.GridLayout;
import java.util.List;

public class TelaEditarPet extends JFrame {

    private static final long serialVersionUID = 1L;

    private final PetService petService;
    private final Pet pet;

    private JTextField txtNome;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtIdade;
    private JTextField txtPeso;
    private JComboBox<Cliente> comboDono;
    private TelaListarPet telaListarPet;

    public TelaEditarPet(PetService petService, Pet pet, TelaListarPet telaListarPet) {
        this.petService = petService;
        this.pet = pet;
        this.telaListarPet = telaListarPet;

        setTitle("Editar Pet");
        setBounds(100, 100, 360, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 2, 0, 0));

        // Nome
        contentPane.add(new JLabel("Nome:"));
        txtNome = new JTextField(pet.getNome());
        contentPane.add(txtNome);

        // Espécie
        contentPane.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField(pet.getEspecie());
        contentPane.add(txtEspecie);

        // Raça
        contentPane.add(new JLabel("Raça:"));
        txtRaca = new JTextField(pet.getRaca());
        contentPane.add(txtRaca);

        // Idade
        contentPane.add(new JLabel("Idade:"));
        txtIdade = new JTextField(String.valueOf(pet.getIdade()));
        contentPane.add(txtIdade);

        // Peso
        contentPane.add(new JLabel("Peso:"));
        txtPeso = new JTextField(String.valueOf(pet.getPeso()));
        contentPane.add(txtPeso);

        // Dono (ComboBox preenchido pelo CSV)
        contentPane.add(new JLabel("Dono:"));
        comboDono = new JComboBox<>();
        ClienteRepository clienteRepo = new ClienteRepository();
        List<Cliente> clientes = clienteRepo.listarTodos();
        Cliente donoAtual = null;

        for (Cliente c : clientes) {
            comboDono.addItem(c);
            if (c.getId() == pet.getClienteId()) {
                donoAtual = c;
            }
        }

        if (donoAtual != null) {
            comboDono.setSelectedItem(donoAtual);
        }
        contentPane.add(comboDono);

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        contentPane.add(btnSalvar);
    }

    private void salvar() {
        try {
            pet.setNome(txtNome.getText());
            pet.setEspecie(txtEspecie.getText());
            pet.setRaca(txtRaca.getText());
            pet.setIdade(Integer.parseInt(txtIdade.getText()));
            pet.setPeso(txtPeso.getText());

            Cliente donoSelecionado = (Cliente) comboDono.getSelectedItem();
            if (donoSelecionado != null) {
                pet.setClienteId(donoSelecionado.getId());
            }

            petService.atualizar(pet); // atualiza CSV do pet

            JOptionPane.showMessageDialog(
                this,
                "Pet atualizado com sucesso."
            );

            telaListarPet.atualizarTabela(); // atualiza a tabela, que deve lidar com ClienteId
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Idade deve ser numérica.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
