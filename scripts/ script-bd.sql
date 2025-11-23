-- =====================================================================
--  Script de criação do banco - ReStart AI
--  Banco-alvo: Microsoft SQL Server / Azure SQL Database
-- =====================================================================

-- ===================== USUARIO =====================
CREATE TABLE USUARIO (
                         USUARIO_ID        BIGINT IDENTITY(1,1) PRIMARY KEY,
                         NOME_COMPLETO     VARCHAR(120)    NOT NULL,
                         CPF               CHAR(11)        NOT NULL,
                         DATA_NASCIMENTO   DATE            NULL,
                         EMAIL             VARCHAR(150)    NOT NULL,
                         SENHA_HASH        VARCHAR(256)    NOT NULL,
                         SENHA_SALT        VARCHAR(128)    NOT NULL,
                         APPLY_CLICKS_HOJE INT             NOT NULL DEFAULT 0,
                         JOBS_VIEWED_HOJE  INT             NOT NULL DEFAULT 0,
                         ULTIMO_EVENTO_EM  DATETIME2       NULL,
                         CRIADO_EM         DATETIME2       NOT NULL DEFAULT SYSDATETIME(),
                         ATUALIZADO_EM     DATETIME2       NULL,
                         CONSTRAINT UQ_USUARIO_EMAIL UNIQUE (EMAIL),
                         CONSTRAINT UQ_USUARIO_CPF   UNIQUE (CPF),
                         CONSTRAINT CK_USUARIO_CPF_DIGITOS CHECK (CPF NOT LIKE '%[^0-9]%'),
                         CONSTRAINT CK_USUARIO_EMAIL_FMT CHECK (EMAIL LIKE '%@%.%')
);

CREATE INDEX IX_USUARIO_CRIADOEM ON USUARIO (CRIADO_EM);



-- ===================== CURRICULO =====================
CREATE TABLE CURRICULO (
                           CURRICULO_ID   BIGINT IDENTITY(1,1) PRIMARY KEY,
                           USUARIO_ID     BIGINT         NOT NULL,
                           TIPO           VARCHAR(10)    NOT NULL,   -- 'PDF' | 'TEXTO'
                           NOME_ARQUIVO   VARCHAR(200)   NULL,
                           TAMANHO_BYTES  BIGINT         NULL,
                           ARQUIVO_URL    VARCHAR(500)   NULL,
                           TEXTO          VARCHAR(MAX)   NULL,
    STATUS         VARCHAR(20)    NOT NULL DEFAULT 'PROCESSADO', -- ENVIADO|PROCESSADO|ERRO
    ANALISADO_EM   DATETIME2      NULL,
    CRIADO_EM      DATETIME2      NOT NULL DEFAULT SYSDATETIME(),
    CONSTRAINT FK_CURRICULO_USUARIO FOREIGN KEY (USUARIO_ID)
        REFERENCES USUARIO(USUARIO_ID) ON DELETE CASCADE,
    CONSTRAINT CK_CURRICULO_TIPO CHECK (TIPO IN ('PDF','TEXTO')),
    CONSTRAINT CK_CURRICULO_STATUS CHECK (STATUS IN ('ENVIADO','PROCESSADO','ERRO'))
);

CREATE INDEX IX_CURRICULO_USUARIO ON CURRICULO (USUARIO_ID, CRIADO_EM DESC);



-- ===================== VAGA =====================
CREATE TABLE VAGA (
                      VAGA_ID        BIGINT IDENTITY(1,1) PRIMARY KEY,
                      TITULO         VARCHAR(120)   NOT NULL,
                      EMPRESA        VARCHAR(120)   NULL,
                      CIDADE         VARCHAR(100)   NULL,
                      SENIORIDADE    VARCHAR(40)    NULL,
                      DESCRICAO      VARCHAR(MAX)   NULL,
    REQ_MUST       VARCHAR(MAX)   NULL,
    REQ_NICE       VARCHAR(MAX)   NULL,
    ATIVA          CHAR(1)        NOT NULL DEFAULT 'S', -- S|N
    CRIADA_EM      DATETIME2      NOT NULL DEFAULT SYSDATETIME(),
    CONSTRAINT CK_VAGA_ATIVA CHECK (ATIVA IN ('S','N'))
);

CREATE INDEX IX_VAGA_ATIVA    ON VAGA (ATIVA);
CREATE INDEX IX_VAGA_CRIADAEM ON VAGA (CRIADA_EM);



-- ===================== CANDIDATURA =====================
CREATE TABLE CANDIDATURA (
                             CANDIDATURA_ID  BIGINT IDENTITY(1,1) PRIMARY KEY,
                             USUARIO_ID      BIGINT        NOT NULL,
                             VAGA_ID         BIGINT        NOT NULL,
                             APLICADA_EM     DATETIME2     NOT NULL DEFAULT SYSDATETIME(),
                             STATUS          VARCHAR(20)   NOT NULL DEFAULT 'ENVIADA', -- ENVIADA|SALVA|ENTREVISTA|RECUSADA|APROVADA
                             SCORE_MATCH     INT           NULL,  -- 0..100
                             WHY_ME          VARCHAR(400)  NULL,
                             APPLY_URL       VARCHAR(500)  NULL,
                             CONSTRAINT FK_CAND_USUARIO FOREIGN KEY (USUARIO_ID)
                                 REFERENCES USUARIO(USUARIO_ID) ON DELETE CASCADE,
                             CONSTRAINT FK_CAND_VAGA FOREIGN KEY (VAGA_ID)
                                 REFERENCES VAGA(VAGA_ID) ON DELETE CASCADE,
                             CONSTRAINT UQ_CAND_USR_VAGA UNIQUE (USUARIO_ID, VAGA_ID),
                             CONSTRAINT CK_CAND_STATUS CHECK (STATUS IN ('ENVIADA','SALVA','ENTREVISTA','RECUSADA','APROVADA')),
                             CONSTRAINT CK_CAND_SCORE  CHECK (SCORE_MATCH IS NULL OR (SCORE_MATCH BETWEEN 0 AND 100))
);

CREATE INDEX IX_CAND_USUARIO_APLICADAEM
    ON CANDIDATURA (USUARIO_ID, APLICADA_EM DESC);
CREATE INDEX IX_CAND_VAGA
    ON CANDIDATURA (VAGA_ID);



-- ===================== NOTIFICACAO =====================
CREATE TABLE NOTIFICACAO (
                             NOTIFICACAO_ID  BIGINT IDENTITY(1,1) PRIMARY KEY,
                             USUARIO_ID      BIGINT         NOT NULL,
                             TITULO          VARCHAR(120)   NOT NULL,
                             MENSAGEM        VARCHAR(500)   NOT NULL,
                             LIDO            CHAR(1)        NOT NULL DEFAULT 'N', -- S|N
                             CRIADO_EM       DATETIME2      NOT NULL DEFAULT SYSDATETIME(),
                             CONSTRAINT FK_NOTIF_USUARIO FOREIGN KEY (USUARIO_ID)
                                 REFERENCES USUARIO(USUARIO_ID) ON DELETE CASCADE,
                             CONSTRAINT CK_NOTIF_LIDO CHECK (LIDO IN ('S','N'))
);

CREATE INDEX IX_NOTIF_USUARIO_LIDO
    ON NOTIFICACAO (USUARIO_ID, LIDO, CRIADO_EM DESC);
