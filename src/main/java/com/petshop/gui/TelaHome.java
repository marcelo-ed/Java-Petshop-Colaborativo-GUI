package com.petshop.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.petshop.gui.cliente.TelaCadastroCli;
import com.petshop.gui.cliente.TelaListarCli;
import com.petshop.gui.pacote.TelaCadastroPacote;
import com.petshop.gui.pet.TelaCadastroPet;
import com.petshop.gui.pet.TelaListarPet;
import com.petshop.gui.servico.TelaCadastroSer;
import com.petshop.gui.servico.TelaListarSer;
import com.petshop.model.Cliente;
import com.petshop.model.Pacote;
import com.petshop.model.Pet;
import com.petshop.model.Servico;
import com.petshop.service.AgendamentoService;
import com.petshop.service.ClienteService;
import com.petshop.service.PacoteService;
import com.petshop.service.PetService;
import com.petshop.service.ServicoService;

public class TelaHome extends JFrame {
    private static final ClienteService clienteService = new ClienteService();
    private static final PetService petService = new PetService();
    private static final ServicoService servicoService = new ServicoService();
    private static final PacoteService pacoteService = new PacoteService();
    private static final AgendamentoService agendamentoService = new AgendamentoService();

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaHome frame = new TelaHome();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void carregarDadosIniciais() {
        System.out.println("Carregando dados iniciais...");

        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setCpf("123.456.789-00");
        cliente1.setEmail("joao.silva@email.com");
        cliente1.setTelefone("(11) 98765-4321");
        cliente1.setEndereco("Rua das Flores, 123");
        clienteService.salvar(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setCpf("987.654.321-00");
        cliente2.setEmail("maria.santos@email.com");
        cliente2.setTelefone("(11) 91234-5678");
        cliente2.setEndereco("Av. Principal, 456");
        clienteService.salvar(cliente2);

        Pet pet1 = new Pet();
        pet1.setNome("Rex");
        pet1.setEspecie("Cachorro");
        pet1.setRaca("Labrador");
        pet1.setIdade(3);
        pet1.setCliente(cliente1);
        petService.salvar(pet1);

        Pet pet2 = new Pet();
        pet2.setNome("Luna");
        pet2.setEspecie("Gato");
        pet2.setRaca("Siamês");
        pet2.setIdade(2);
        pet2.setCliente(cliente1);
        petService.salvar(pet2);

        Pet pet3 = new Pet();
        pet3.setNome("Thor");
        pet3.setEspecie("Cachorro");
        pet3.setRaca("Golden Retriever");
        pet3.setIdade(1);
        pet3.setCliente(cliente2);
        petService.salvar(pet3);

        Servico servico1 = new Servico();
        servico1.setNome("Banho");
        servico1.setDescricao("Banho completo com produtos especiais");
        servico1.setPreco(new BigDecimal("50.00"));
        servico1.setDuracaoMinutos(30);
        servicoService.salvar(servico1);

        Servico servico2 = new Servico();
        servico2.setNome("Tosa");
        servico2.setDescricao("Tosa completa com acabamento");
        servico2.setPreco(new BigDecimal("80.00"));
        servico2.setDuracaoMinutos(60);
        servicoService.salvar(servico2);

        Servico servico3 = new Servico();
        servico3.setNome("Consulta Veterinária");
        servico3.setDescricao("Consulta de rotina com veterinário");
        servico3.setPreco(new BigDecimal("150.00"));
        servico3.setDuracaoMinutos(30);
        servicoService.salvar(servico3);

        Pacote pacote1 = new Pacote();
        pacote1.setNome("Pacote Banho e Tosa");
        pacote1.setDescricao("Banho e tosa com desconto especial");
        pacote1.setDesconto(new BigDecimal("20.00"));
        pacote1.getServicos().add(servico1);
        pacote1.getServicos().add(servico2);
        pacoteService.criarPacote(pacote1);

        Pacote pacote2 = new Pacote();
        pacote2.setNome("Pacote Completo");
        pacote2.setDescricao("Banho, tosa e consulta veterinária");
        pacote2.setDesconto(new BigDecimal("15.00"));
        pacote2.getServicos().add(servico1);
        pacote2.getServicos().add(servico2);
        pacote2.getServicos().add(servico3);
        pacoteService.criarPacote(pacote2);

        System.out.println("Dados iniciais carregados com sucesso!");
    }

    public TelaHome() {
         carregarDadosIniciais();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCliente = new JMenu("Cliente");
        menuBar.add(menuCliente);

        JMenuItem itemClienteCadastro = new JMenuItem("Cadastro");
        itemClienteCadastro.addActionListener(e -> new TelaCadastroCli(clienteService));
        menuCliente.add(itemClienteCadastro);

        JMenuItem itemClienteListar = new JMenuItem("Listar");
        itemClienteListar.addActionListener(e -> {
            TelaListarCli telaListarCli = new TelaListarCli(clienteService);
            telaListarCli.setVisible(true);
        });
        menuCliente.add(itemClienteListar);

        JMenu meuPets = new JMenu("Pets");
        menuBar.add(meuPets);

        JMenuItem itemPetCadastrar = new JMenuItem("Cadastrar");
        itemPetCadastrar.addActionListener(e -> new TelaCadastroPet(petService, clienteService).setVisible(true));
        meuPets.add(itemPetCadastrar);

        JMenuItem itemPetListar = new JMenuItem("Listar");
        itemPetListar.addActionListener(e -> new TelaListarPet(petService, servicoService, pacoteService).setVisible(true));
        meuPets.add(itemPetListar);

        JMenu menuServico = new JMenu("Serviços");
        menuBar.add(menuServico);

        JMenuItem itemServicosCadastro = new JMenuItem("Cadastro");
        itemServicosCadastro.addActionListener(e -> new TelaCadastroSer(servicoService).setVisible(true));
        menuServico.add(itemServicosCadastro);

        JMenuItem itemServicosListar = new JMenuItem("Listar");
        itemServicosListar.addActionListener(e -> new TelaListarSer(servicoService).setVisible(true));
        menuServico.add(itemServicosListar);

        JMenu menuPacotes = new JMenu("Pacotes");
        menuBar.add(menuPacotes);

        JMenuItem itemPacotesContratar = new JMenuItem("Cadastrar");
        itemPacotesContratar.addActionListener(e -> new TelaCadastroPacote(pacoteService, servicoService).setVisible(true));
        menuPacotes.add(itemPacotesContratar);

        Component horizontalGlue = Box.createHorizontalGlue();
        menuBar.add(horizontalGlue);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSair.setForeground(new Color(64, 0, 0));
        btnSair.addActionListener(e -> System.exit(0));
        menuBar.add(btnSair);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(new Color(245, 245, 245));
        painelCentral.setLayout(new GridLayout(3, 1, 5, 5));

        JButton infoClientes = new JButton("Clientes cadastrados: " + clienteService.listarTodos().size());
        infoClientes.setEnabled(false);
        infoClientes.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton infoPets = new JButton("Pets cadastrados: " + petService.listarTodos().size());
        infoPets.setEnabled(false);
        infoPets.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton infoServicos = new JButton("Serviços cadastrados: " + servicoService.listarTodos().size());
        infoServicos.setEnabled(false);
        infoServicos.setFont(new Font("Segoe UI", Font.BOLD, 16));

        painelCentral.add(infoClientes);
        painelCentral.add(infoPets);
        painelCentral.add(infoServicos);

        contentPane.add(painelCentral, BorderLayout.CENTER);
    }
}
