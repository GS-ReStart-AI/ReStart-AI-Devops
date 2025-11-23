package br.com.restartai.restart_ai.repository;

import br.com.restartai.restart_ai.domain.Candidatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

    Page<Candidatura> findByUsuario_Id(Long usuarioId, Pageable pageable);

    Page<Candidatura> findByVaga_Id(Long vagaId, Pageable pageable);

    boolean existsByUsuario_IdAndVaga_Id(Long usuarioId, Long vagaId);
}
