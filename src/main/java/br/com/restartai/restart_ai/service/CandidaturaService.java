package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Candidatura;
import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.domain.Vaga;
import br.com.restartai.restart_ai.dto.candidatura.CandidaturaCadastroDTO;
import br.com.restartai.restart_ai.repository.CandidaturaRepository;
import br.com.restartai.restart_ai.repository.UsuarioRepository;
import br.com.restartai.restart_ai.repository.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VagaRepository vagaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository,
                              UsuarioRepository usuarioRepository,
                              VagaRepository vagaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.usuarioRepository = usuarioRepository;
        this.vagaRepository = vagaRepository;
    }

    public Candidatura criar(CandidaturaCadastroDTO dto) {
        if (candidaturaRepository.existsByUsuario_IdAndVaga_Id(dto.getUsuarioId(), dto.getVagaId())) {
            throw new IllegalStateException("Usuário já possui candidatura para esta vaga.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Vaga vaga = vagaRepository.findById(dto.getVagaId())
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada."));

        Candidatura candidatura = new Candidatura();
        candidatura.setUsuario(usuario);
        candidatura.setVaga(vaga);
        candidatura.setStatus(dto.getStatus() != null ? dto.getStatus() : "ENVIADA");
        candidatura.setScoreMatch(dto.getScoreMatch());
        candidatura.setWhyMe(dto.getWhyMe());
        candidatura.setApplyUrl(dto.getApplyUrl());
        candidatura.setAplicadaEm(LocalDateTime.now());

        return candidaturaRepository.save(candidatura);
    }

    public Page<Candidatura> listar(Pageable pageable) {
        return candidaturaRepository.findAll(pageable);
    }

    public Page<Candidatura> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return candidaturaRepository.findByUsuario_Id(usuarioId, pageable);
    }

    public Page<Candidatura> listarPorVaga(Long vagaId, Pageable pageable) {
        return candidaturaRepository.findByVaga_Id(vagaId, pageable);
    }

    public Candidatura buscarPorId(Long id) {
        return candidaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada."));
    }

    public Candidatura atualizar(Long id, CandidaturaCadastroDTO dto) {
        Candidatura candidatura = candidaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada."));

        if (!candidatura.getUsuario().getId().equals(dto.getUsuarioId())) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
            candidatura.setUsuario(usuario);
        }

        if (!candidatura.getVaga().getId().equals(dto.getVagaId())) {
            Vaga vaga = vagaRepository.findById(dto.getVagaId())
                    .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada."));
            candidatura.setVaga(vaga);
        }

        if (dto.getStatus() != null) {
            candidatura.setStatus(dto.getStatus());
        }
        candidatura.setScoreMatch(dto.getScoreMatch());
        candidatura.setWhyMe(dto.getWhyMe());
        candidatura.setApplyUrl(dto.getApplyUrl());

        return candidaturaRepository.save(candidatura);
    }

    public void excluir(Long id) {
        if (!candidaturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Candidatura não encontrada.");
        }
        candidaturaRepository.deleteById(id);
    }
}
