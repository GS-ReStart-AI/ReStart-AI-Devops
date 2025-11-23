package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.usuario.UsuarioCadastroDTO;
import br.com.restartai.restart_ai.dto.usuario.UsuarioPerfilAtualizacaoDTO;
import br.com.restartai.restart_ai.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registrarDeveCriarUsuarioQuandoDadosValidos() {
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO();
        dto.setNomeCompleto("Usuaria Teste");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(1990, 1, 1));
        dto.setEmail("teste@example.com");
        dto.setSenha("senhaSegura");
        dto.setConfirmarSenha("senhaSegura");

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByCpf(dto.getCpf())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("HASHED");

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        Usuario usuario = usuarioService.registrar(dto);

        assertNotNull(usuario.getId());
        assertEquals(dto.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(dto.getCpf(), usuario.getCpf());
        assertEquals(dto.getEmail(), usuario.getEmail());
        assertEquals("HASHED", usuario.getSenhaHash());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void registrarDeveLancarExcecaoQuandoSenhasNaoConferem() {
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO();
        dto.setNomeCompleto("Usuaria Teste");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(1990, 1, 1));
        dto.setEmail("teste@example.com");
        dto.setSenha("senhaSegura");
        dto.setConfirmarSenha("outraSenha");

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(dto));
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    void registrarDeveLancarExcecaoQuandoEmailJaExiste() {
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO();
        dto.setNomeCompleto("Usuaria Teste");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(1990, 1, 1));
        dto.setEmail("teste@example.com");
        dto.setSenha("senhaSegura");
        dto.setConfirmarSenha("senhaSegura");

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void buscarPorIdDeveRetornarUsuarioQuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarPorId(1L));
    }

    @Test
    void autenticarDeveAtualizarUltimoEventoQuandoCredenciaisValidas() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@example.com");
        usuario.setSenhaSalt("SALT");
        usuario.setSenhaHash("HASH");

        when(usuarioRepository.findByEmail("teste@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario autenticado = usuarioService.autenticar("teste@example.com", "senhaSegura");

        assertEquals(usuario, autenticado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void autenticarDeveLancarExcecaoQuandoSenhaInvalida() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@example.com");
        usuario.setSenhaSalt("SALT");
        usuario.setSenhaHash("HASH");

        when(usuarioRepository.findByEmail("teste@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.autenticar("teste@example.com", "senhaErrada"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void atualizarPerfilDeveAtualizarDadosQuandoValido() {
        UsuarioPerfilAtualizacaoDTO dto = new UsuarioPerfilAtualizacaoDTO();
        dto.setNomeCompleto("Nome Atualizado");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(1991, 2, 2));

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf("12345678901");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario atualizado = usuarioService.atualizarPerfil(1L, dto);

        assertEquals(dto.getNomeCompleto(), atualizado.getNomeCompleto());
        assertEquals(dto.getCpf(), atualizado.getCpf());
        verify(usuarioRepository).save(usuario);
    }
}
