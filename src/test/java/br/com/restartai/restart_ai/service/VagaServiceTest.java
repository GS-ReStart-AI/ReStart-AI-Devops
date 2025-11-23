package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Vaga;
import br.com.restartai.restart_ai.dto.vaga.VagaCadastroDTO;
import br.com.restartai.restart_ai.repository.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VagaServiceTest {

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private VagaService vagaService;

    @Test
    void criarDeveSalvarVagaComDadosDoDto() {
        VagaCadastroDTO dto = new VagaCadastroDTO();
        dto.setTitulo("Desenvolvedora Java");
        dto.setEmpresa("Restart.Ai");
        dto.setCidade("São Paulo");
        dto.setSenioridade("PLENO");
        dto.setDescricao("Vaga para desenvolvimento backend");
        dto.setReqMust("Java, Spring");
        dto.setReqNice("Docker, Kubernetes");
        dto.setAtiva("S");

        when(vagaRepository.save(any(Vaga.class))).thenAnswer(invocation -> {
            Vaga v = invocation.getArgument(0);
            v.setId(10L);
            return v;
        });

        Vaga vaga = vagaService.criar(dto);

        assertNotNull(vaga.getId());
        assertEquals(dto.getTitulo(), vaga.getTitulo());
        assertEquals(dto.getEmpresa(), vaga.getEmpresa());
        assertEquals(dto.getCidade(), vaga.getCidade());
        assertEquals(dto.getSenioridade(), vaga.getSenioridade());
        assertEquals(dto.getDescricao(), vaga.getDescricao());
        assertEquals(dto.getReqMust(), vaga.getReqMust());
        assertEquals(dto.getReqNice(), vaga.getReqNice());
        assertEquals(dto.getAtiva(), vaga.getAtiva());
        verify(vagaRepository).save(any(Vaga.class));
    }

    @Test
    void listarDeveRetornarPaginaDeVagas() {
        Pageable pageable = PageRequest.of(0, 10);
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        Page<Vaga> page = new PageImpl<>(Collections.singletonList(vaga), pageable, 1);

        when(vagaRepository.findAll(pageable)).thenReturn(page);

        Page<Vaga> resultado = vagaService.listar(pageable);

        assertEquals(1, resultado.getTotalElements());
        verify(vagaRepository).findAll(pageable);
    }

    @Test
    void listarApenasAtivasDeveChamarRepositorioComSomenteAtivas() {
        Pageable pageable = PageRequest.of(0, 10);
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        Page<Vaga> page = new PageImpl<>(Collections.singletonList(vaga), pageable, 1);

        when(vagaRepository.findByAtiva("S", pageable)).thenReturn(page);

        Page<Vaga> resultado = vagaService.listarApenasAtivas(pageable);

        assertEquals(1, resultado.getTotalElements());
        verify(vagaRepository).findByAtiva("S", pageable);
    }

    @Test
    void buscarPorIdDeveRetornarVagaQuandoExiste() {
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga));

        Vaga resultado = vagaService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoExiste() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> vagaService.buscarPorId(1L));
    }

    @Test
    void excluirDeveRemoverVagaQuandoExiste() {
        when(vagaRepository.existsById(1L)).thenReturn(true);

        vagaService.excluir(1L);

        verify(vagaRepository).deleteById(1L);
    }

    @Test
    void excluirDeveLancarExcecaoQuandoVagaNaoExiste() {
        when(vagaRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> vagaService.excluir(1L));
        verify(vagaRepository, never()).deleteById(anyLong());
    }
}
