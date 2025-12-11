package com.petshop.gui.pet;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.petshop.model.Pet;
import com.petshop.service.PetService;

public class TelaEscolhaPet extends JFrame {

    private static final long serialVersionUID = 1L;

    private PetService petService;
    private Pet pet;
    TelaListarPet telaListarPet;
    public TelaEscolhaPet(PetService petService, Pet pet, TelaListarPet telaListarPet) {
    	this.telaListarPet = telaListarPet;
        this.petService = petService;
        this.pet = pet;

        setTitle("Opções do Pet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 380, 160);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 10));
        setContentPane(contentPane);

        JLabel lblPergunta = new JLabel(
            "O que você deseja fazer com o pet selecionado?"
        );
        lblPergunta.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPergunta.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblPergunta, BorderLayout.NORTH);

        JPanel panelBotoes = new JPanel();
        contentPane.add(panelBotoes, BorderLayout.CENTER);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExcluir.addActionListener(e -> excluirPet());
        panelBotoes.add(btnExcluir);

        JButton btnEditar = new JButton("Alterar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnEditar.addActionListener(e -> editarPet());
        panelBotoes.add(btnEditar);
    }

    private void excluirPet() {
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir este pet?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            petService.deletar(pet.getId());
            JOptionPane.showMessageDialog(
                this,
                "Pet excluído com sucesso."
            );
            telaListarPet.atualizarTabela();
            dispose();
        }
    }

    private void editarPet() {
        new TelaEditarPet(petService, pet, telaListarPet).setVisible(true);
        dispose();
    }
}
