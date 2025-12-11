package com.petshop.gui.cliente;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.petshop.model.Cliente;
import com.petshop.service.ClienteService;

public class TelaEscolhaCli extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	
	private int telaConfirmacao() {
		int resposta = JOptionPane.showConfirmDialog(
			    this,
			    "Tem certeza que deseja excluir este cliente?",
			    "Confirmação",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.WARNING_MESSAGE
			);

			return(resposta);
			
	}
	/**
	 * Create the frame.
	 */
	ClienteService clienteService;
	public TelaEscolhaCli(ClienteService clienteService, Cliente cliente) {
		this.clienteService = clienteService;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 146);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel textAsk = new JLabel("O que você deseja fazer com o cliente selecionado?");
		textAsk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textAsk.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textAsk, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JButton btnExcluirCliente = new JButton("Excluir");
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (telaConfirmacao() == JOptionPane.YES_OPTION) {
				    clienteService.deletar(cliente.getId());
				    JOptionPane.showMessageDialog(null, "O cliente foi excluído.");
				    dispose();
				}

			}
		});
		btnExcluirCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(btnExcluirCliente);
		
		JButton btnNewButton_1 = new JButton("Alterar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEditarCli telaEditarCli = new TelaEditarCli(clienteService, cliente);
				telaEditarCli.setVisible(true);
				
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(btnNewButton_1);

	}

}
