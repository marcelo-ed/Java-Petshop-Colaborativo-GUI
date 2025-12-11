package com.petshop.repository;

import com.petshop.model.Pacote;
import com.petshop.service.PacoteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacoteContratadoRepository {

    private static final List<PacoteContratado> pacotesContratados = new ArrayList<>();

    public void salvar(Long petId, Long pacoteId, LocalDate data) {
        PacoteContratado pc = new PacoteContratado();
        pc.setPetId(petId);
        pc.setPacoteId(pacoteId);
        pc.setDataContratacao(data);
        pacotesContratados.add(pc);
    }

    public List<Pacote> buscarPacotesPorPet(Long petId, PacoteService pacoteService) {
        List<Pacote> resultado = new ArrayList<>();
        for (PacoteContratado pc : pacotesContratados) {
            if (pc.getPetId().equals(petId)) {
                Optional<Pacote> pacote = pacoteService.buscarPorId(pc.getPacoteId());
                pacote.ifPresent(resultado::add); 
            }
        }
        return resultado;
    }

    public static class PacoteContratado {
        private Long petId;
        private Long pacoteId;
        private LocalDate dataContratacao;

        public Long getPetId() { return petId; }
        public void setPetId(Long petId) { this.petId = petId; }

        public Long getPacoteId() { return pacoteId; }
        public void setPacoteId(Long pacoteId) { this.pacoteId = pacoteId; }

        public LocalDate getDataContratacao() { return dataContratacao; }
        public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    }
}
