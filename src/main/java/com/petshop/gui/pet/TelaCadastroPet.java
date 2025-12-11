package com.petshop.gui.pet;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.petshop.model.Cliente;
import com.petshop.model.Pet;
import com.petshop.service.ClienteService;
import com.petshop.service.PetService;

public class TelaCadastroPet extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputNome;
	private JTextField inputEspecie;
	private JTextField inputRaca;
	private JTextField inputIdade;
	private JTextField inputPeso;

	/**
	 * Create the frame.
	 */
	public TelaCadastroPet(PetService petService, ClienteService clienteService) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 2, 10, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		inputNome = new JTextField();
		panel.add(inputNome);
		inputNome.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Espécie:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		inputEspecie = new JTextField();
		panel.add(inputEspecie);
		inputEspecie.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Raça:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		inputRaca = new JTextField();
		panel.add(inputRaca);
		inputRaca.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Idade:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		inputIdade = new JTextField();
		panel.add(inputIdade);
		inputIdade.setColumns(10);
		
		JLabel labelPeso = new JLabel("Peso:");
		labelPeso.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(labelPeso);
		
		inputPeso = new JTextField();
		inputPeso.setColumns(10);
		panel.add(inputPeso);
		
		JLabel lblNewLabel_5_1 = new JLabel("Dono:");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5_1);
		
		JComboBox<Cliente> comboBoxCliente = new JComboBox<>();
		panel.add(comboBoxCliente);
		for (Cliente c : clienteService.listarTodos()) {
			comboBoxCliente.addItem(c);
		}
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pet pet = new Pet();
				pet.setNome(inputNome.getText());	
				
				pet.setEspecie(inputEspecie.getText());
				try {
				    pet.setIdade(Integer.parseInt(inputIdade.getText()));
				} catch (NumberFormatException ex) {
				    JOptionPane.showMessageDialog(
				        TelaCadastroPet.this,
				        "Idade inválida.",
				        "Erro",
				        JOptionPane.ERROR_MESSAGE
				    );
				    return;
				}
				pet.setPeso(inputPeso.getText());

				
				
				pet.setRaca(inputRaca.getText());
				pet.setCliente((Cliente) comboBoxCliente.getSelectedItem());
				petService.salvar(pet);
				JOptionPane.showMessageDialog(null, 
						"Cadastro realizado com sucesso.", 
						"Cadastro de Pet", 
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		panel_1.add(btnNewButton);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());


		panel_1.add(btnCancelar);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Pets");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		setVisible(true);
	}

}
