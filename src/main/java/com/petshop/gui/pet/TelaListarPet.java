package com.petshop.gui.pet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.petshop.model.Cliente;
import com.petshop.model.Pacote;
import com.petshop.model.Pet;
import com.petshop.model.Servico;
import com.petshop.model.ServicoContratado;
import com.petshop.repository.ClienteRepository;
import com.petshop.repository.PacoteContratadoRepository;
import com.petshop.repository.ServicoContratadoRepository;
import com.petshop.service.PacoteService;
import com.petshop.service.PetService;
import com.petshop.service.ServicoService;

public class TelaListarPet extends JFrame {

    private static final long serialVersionUID = 1L;

    private final PetService petService;
    private final ServicoService servicoService;
    private final PacoteService pacoteService;
    private final ServicoContratadoRepository servicoContratadoRepo;
    private final PacoteContratadoRepository pacoteContratadoRepo;

    private JTable table;
    private JTextField txtPesquisar;
    private JButton btnPesquisar, btnAtualizar, btnEditar, btnExcluir, btnContratarServico, btnContratarPacote, btnMostrarContratados;

    public TelaListarPet(PetService petService, ServicoService servicoService, PacoteService pacoteService) {
        this.petService = petService;
        this.servicoService = servicoService;
        this.pacoteService = pacoteService;
        this.servicoContratadoRepo = new ServicoContratadoRepository();
        this.pacoteContratadoRepo = new PacoteContratadoRepository();

        setTitle("Listar Pets");
        setBounds(100, 100, 1000, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtPesquisar = new JTextField(20);
        btnPesquisar = new JButton("Pesquisar");
        btnAtualizar = new JButton("Atualizar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnContratarServico = new JButton("Contratar Serviço");
        btnContratarPacote = new JButton("Contratar Pacote");
        btnMostrarContratados = new JButton("Mostrar Contratados");

        topo.add(txtPesquisar);
        topo.add(btnPesquisar);
        topo.add(btnAtualizar);
        topo.add(btnEditar);
        topo.add(btnExcluir);
        topo.add(btnContratarServico);
        topo.add(btnContratarPacote);
        topo.add(btnMostrarContratados);

        add(topo, BorderLayout.NORTH);

        table = new JTable();
        table.setDefaultEditor(Object.class, null); 
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        atualizarTabela();

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnPesquisar.addActionListener(e -> pesquisar(txtPesquisar.getText()));
        btnEditar.addActionListener(e -> editarPet());
        btnExcluir.addActionListener(e -> excluirPet());
        btnContratarServico.addActionListener(e -> contratarServico());
        btnContratarPacote.addActionListener(e -> contratarPacote());
        btnMostrarContratados.addActionListener(e -> mostrarContratados());
    }

    public void atualizarTabela() {
        String[] colunas = { "ID", "Nome", "Espécie", "Raça", "Idade", "Peso", "Dono" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Pet> pets = petService.listarTodos();
        ClienteRepository clienteRepo = new ClienteRepository();
        List<Cliente> clientes = clienteRepo.listarTodos();

        for (Pet pet : pets) {
            Cliente dono = clientes.stream()
                    .filter(c -> c.getId().equals(pet.getClienteId()))
                    .findFirst()
                    .orElse(null);
            String nomeDono = dono != null ? dono.getNome() : "Sem dono";

            Object[] linha = {
                    pet.getId(),
                    pet.getNome(),
                    pet.getEspecie(),
                    pet.getRaca(),
                    pet.getIdade(),
                    pet.getPeso(),
                    nomeDono
            };
            model.addRow(linha);
        }

        table.setModel(model);
    }

    private void pesquisar(String termo) {
        String[] colunas = { "ID", "Nome", "Espécie", "Raça", "Idade", "Peso", "Dono" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Pet> pets = petService.listarTodos();
        ClienteRepository clienteRepo = new ClienteRepository();
        List<Cliente> clientes = clienteRepo.listarTodos();

        for (Pet pet : pets) {
            if (pet.getNome().toLowerCase().contains(termo.toLowerCase())) {
                Cliente dono = clientes.stream()
                        .filter(c -> c.getId().equals(pet.getClienteId()))
                        .findFirst()
                        .orElse(null);
                String nomeDono = dono != null ? dono.getNome() : "Sem dono";

                Object[] linha = {
                        pet.getId(),
                        pet.getNome(),
                        pet.getEspecie(),
                        pet.getRaca(),
                        pet.getIdade(),
                        pet.getPeso(),
                        nomeDono
                };
                model.addRow(linha);
            }
        }

        table.setModel(model);
    }

    private void editarPet() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Long idPet = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());
            petService.buscarPorId(idPet).ifPresent(pet -> {
                TelaEditarPet telaEditar = new TelaEditarPet(petService, pet, this);
                telaEditar.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pet para editar.");
        }
    }

    private void excluirPet() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o pet?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Long idPet = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());
                petService.deletar(idPet);
                atualizarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pet para excluir.");
        }
    }

    private void contratarServico() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pet para contratar serviço.");
            return;
        }

        Long idPet = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());
        Pet pet = petService.buscarPorId(idPet).orElse(null);
        if (pet == null) return;

        List<Servico> servicos = servicoService.listarTodos();
        if (servicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há serviços cadastrados.");
            return;
        }

        String[] nomesServicos = servicos.stream().map(Servico::getNome).toArray(String[]::new);
        JList<String> listaServicos = new JList<>(nomesServicos);
        listaServicos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(listaServicos),
                "Selecione serviços para " + pet.getNome(),
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            int[] selecionados = listaServicos.getSelectedIndices();
            for (int i : selecionados) {
                servicoService.buscarPorId(servicos.get(i).getId()).ifPresent(s -> {
                    ServicoContratado sc = new ServicoContratado();
                    sc.setPetId(pet.getId());
                    sc.setServicoId(s.getId());
                    sc.setDataContratacao(LocalDate.now());
                    servicoContratadoRepo.salvar(sc);
                });
            }
            JOptionPane.showMessageDialog(this, "Serviços contratados com sucesso para " + pet.getNome());
        }
    }

    private void contratarPacote() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pet para contratar pacote.");
            return;
        }

        Long idPet = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());
        Pet pet = petService.buscarPorId(idPet).orElse(null);
        if (pet == null) return;

        List<Pacote> pacotes = pacoteService.listarTodos();
        if (pacotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há pacotes cadastrados.");
            return;
        }

        String[] nomesPacotes = pacotes.stream().map(Pacote::getNome).toArray(String[]::new);
        JList<String> listaPacotes = new JList<>(nomesPacotes);
        listaPacotes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(listaPacotes),
                "Selecione pacotes para " + pet.getNome(),
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            int[] selecionados = listaPacotes.getSelectedIndices();
            for (int i : selecionados) {
                pacoteService.buscarPorId(pacotes.get(i).getId()).ifPresent(p -> {
                    pacoteContratadoRepo.salvar(idPet, p.getId(), LocalDate.now());
                });
            }
            JOptionPane.showMessageDialog(this, "Pacotes contratados com sucesso para " + pet.getNome());
        }
    }

    private void mostrarContratados() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pet.");
            return;
        }

        Long idPet = Long.valueOf(table.getValueAt(linhaSelecionada, 0).toString());

        List<ServicoContratado> servicosContratados = servicoContratadoRepo.buscarPorPet(idPet);
        List<Pacote> pacotesContratados = pacoteContratadoRepo.buscarPacotesPorPet(idPet, pacoteService);

        JPanel painel = new JPanel(new GridLayout(2, 1, 10, 10));

        String[] colServicos = { "Serviço", "Data" };
        DefaultTableModel modelServicos = new DefaultTableModel(colServicos, 0);
        for (ServicoContratado sc : servicosContratados) {
            servicoService.buscarPorId(sc.getServicoId()).ifPresent(s -> {
                modelServicos.addRow(new Object[]{ s.getNome(), sc.getDataContratacao() });
            });
        }
        JTable tableServicos = new JTable(modelServicos);
        tableServicos.setDefaultEditor(Object.class, null);
        painel.add(new JScrollPane(tableServicos));

        String[] colPacotes = { "Pacote", "Data" };
        DefaultTableModel modelPacotes = new DefaultTableModel(colPacotes, 0);
        for (Pacote p : pacotesContratados) {
            modelPacotes.addRow(new Object[]{ p.getNome(), "—" });
        }
        JTable tablePacotes = new JTable(modelPacotes);
        tablePacotes.setDefaultEditor(Object.class, null);
        painel.add(new JScrollPane(tablePacotes));

        JOptionPane.showMessageDialog(this, painel, "Serviços e Pacotes contratados", JOptionPane.INFORMATION_MESSAGE);
    }
}
