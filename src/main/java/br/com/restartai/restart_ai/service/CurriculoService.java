package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Curriculo;
import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.curriculo.CurriculoCadastroDTO;
import br.com.restartai.restart_ai.repository.CurriculoRepository;
import br.com.restartai.restart_ai.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final UsuarioRepository usuarioRepository;

    public CurriculoService(CurriculoRepository curriculoRepository,
                            UsuarioRepository usuarioRepository) {
        this.curriculoRepository = curriculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Curriculo criar(CurriculoCadastroDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Curriculo curriculo = new Curriculo();
        curriculo.setUsuario(usuario);
        curriculo.setTipo(dto.getTipo());
        curriculo.setNomeArquivo(dto.getNomeArquivo());
        curriculo.setTamanhoBytes(dto.getTamanhoBytes());
        curriculo.setArquivoUrl(dto.getArquivoUrl());
        curriculo.setTexto(dto.getTexto());
        curriculo.setStatus("ENVIADO");
        curriculo.setCriadoEm(LocalDateTime.now());

        return curriculoRepository.save(curriculo);
    }

    public Page<Curriculo> listar(Pageable pageable) {
        return curriculoRepository.findAll(pageable);
    }

    public Page<Curriculo> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return curriculoRepository.findByUsuario_Id(usuarioId, pageable);
    }

    public Curriculo buscarPorId(Long id) {
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado."));
    }

    public Curriculo atualizar(Long id, CurriculoCadastroDTO dto) {
        Curriculo curriculo = curriculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado."));

        if (!curriculo.getUsuario().getId().equals(dto.getUsuarioId())) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
            curriculo.setUsuario(usuario);
        }

        curriculo.setTipo(dto.getTipo());
        curriculo.setNomeArquivo(dto.getNomeArquivo());
        curriculo.setTamanhoBytes(dto.getTamanhoBytes());
        curriculo.setArquivoUrl(dto.getArquivoUrl());
        curriculo.setTexto(dto.getTexto());

        return curriculoRepository.save(curriculo);
    }

    public void excluir(Long id) {
        if (!curriculoRepository.existsById(id)) {
            throw new EntityNotFoundException("Currículo não encontrado.");
        }
        curriculoRepository.deleteById(id);
    }
}
