package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Vaga;
import br.com.restartai.restart_ai.dto.vaga.VagaCadastroDTO;
import br.com.restartai.restart_ai.repository.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public Vaga criar(VagaCadastroDTO dto) {
        Vaga vaga = new Vaga();
        vaga.setTitulo(dto.getTitulo());
        vaga.setEmpresa(dto.getEmpresa());
        vaga.setCidade(dto.getCidade());
        vaga.setSenioridade(dto.getSenioridade());
        vaga.setDescricao(dto.getDescricao());
        vaga.setReqMust(dto.getReqMust());
        vaga.setReqNice(dto.getReqNice());
        vaga.setAtiva(dto.getAtiva());
        vaga.setCriadaEm(LocalDateTime.now());
        return vagaRepository.save(vaga);
    }

    public Page<Vaga> listar(Pageable pageable) {
        return vagaRepository.findAll(pageable);
    }

    public Page<Vaga> listarApenasAtivas(Pageable pageable) {
        return vagaRepository.findByAtiva("S", pageable);
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada."));
    }

    public Vaga atualizar(Long id, VagaCadastroDTO dto) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada."));

        vaga.setTitulo(dto.getTitulo());
        vaga.setEmpresa(dto.getEmpresa());
        vaga.setCidade(dto.getCidade());
        vaga.setSenioridade(dto.getSenioridade());
        vaga.setDescricao(dto.getDescricao());
        vaga.setReqMust(dto.getReqMust());
        vaga.setReqNice(dto.getReqNice());
        vaga.setAtiva(dto.getAtiva());

        return vagaRepository.save(vaga);
    }

    public void excluir(Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new EntityNotFoundException("Vaga não encontrada.");
        }
        vagaRepository.deleteById(id);
    }
}
