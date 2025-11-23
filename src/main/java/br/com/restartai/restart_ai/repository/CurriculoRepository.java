package br.com.restartai.restart_ai.repository;

import br.com.restartai.restart_ai.domain.Curriculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {

    Page<Curriculo> findByUsuario_Id(Long usuarioId, Pageable pageable);
}
