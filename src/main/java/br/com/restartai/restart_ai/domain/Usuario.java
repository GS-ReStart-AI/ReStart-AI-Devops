package br.com.restartai.restart_ai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
    private Long id;

    @Column(name = "NOME_COMPLETO", length = 120, nullable = false)
    private String nomeCompleto;

    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "EMAIL", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "SENHA_HASH", length = 256, nullable = false)
    private String senhaHash;

    @Column(name = "SENHA_SALT", length = 128, nullable = false)
    private String senhaSalt;

    @Column(name = "APPLY_CLICKS_HOJE", nullable = false)
    private Integer applyClicksHoje = 0;

    @Column(name = "JOBS_VIEWED_HOJE", nullable = false)
    private Integer jobsViewedHoje = 0;

    @Column(name = "ULTIMO_EVENTO_EM")
    private LocalDateTime ultimoEventoEm;

    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ATUALIZADO_EM")
    private LocalDateTime atualizadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getSenhaSalt() {
        return senhaSalt;
    }

    public void setSenhaSalt(String senhaSalt) {
        this.senhaSalt = senhaSalt;
    }

    public Integer getApplyClicksHoje() {
        return applyClicksHoje;
    }

    public void setApplyClicksHoje(Integer applyClicksHoje) {
        this.applyClicksHoje = applyClicksHoje;
    }

    public Integer getJobsViewedHoje() {
        return jobsViewedHoje;
    }

    public void setJobsViewedHoje(Integer jobsViewedHoje) {
        this.jobsViewedHoje = jobsViewedHoje;
    }

    public LocalDateTime getUltimoEventoEm() {
        return ultimoEventoEm;
    }

    public void setUltimoEventoEm(LocalDateTime ultimoEventoEm) {
        this.ultimoEventoEm = ultimoEventoEm;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
