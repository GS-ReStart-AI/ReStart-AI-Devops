package br.com.restartai.restart_ai.repository;

import br.com.restartai.restart_ai.domain.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Page<Vaga> findByAtiva(String ativa, Pageable pageable);
}
