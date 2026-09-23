-- DDL para SQL Server: esquema para o sistema de reservas

-- criar schema se não existir
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'reserva_lab_salas')
    EXEC('CREATE SCHEMA reserva_lab_salas');

CREATE TABLE reserva_lab_salas.usuarios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(150) NOT NULL,
    data_aniversario DATE NOT NULL,
    celular VARCHAR(15) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL
);

CREATE TABLE reserva_lab_salas.laboratorios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(150) NOT NULL,
    capacidade INT NOT NULL CHECK (capacidade > 0),
    localizacao VARCHAR(200) NOT NULL
);

CREATE TABLE reserva_lab_salas.salas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(150) NOT NULL,
    capacidade INT NOT NULL CHECK (capacidade > 0),
    localizacao VARCHAR(200) NOT NULL
);

CREATE TABLE reserva_lab_salas.statuses (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE reserva_lab_salas.reservas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    laboratorio_id BIGINT NULL,
    sala_id BIGINT NULL,
    status_id BIGINT NOT NULL,
    CONSTRAINT CHK_Recurso_XOR CHECK ( (laboratorio_id IS NOT NULL AND sala_id IS NULL) OR (laboratorio_id IS NULL AND sala_id IS NOT NULL) ),
    CONSTRAINT CHK_DataHora_Consistencia CHECK (data_fim >= data_inicio AND (data_fim > data_inicio OR hora_fim > hora_inicio))
);

-- FKs
ALTER TABLE reserva_lab_salas.reservas
    ADD CONSTRAINT FK_Reserva_Usuario FOREIGN KEY (usuario_id) REFERENCES reserva_lab_salas.usuarios(id) ON DELETE NO ACTION;

ALTER TABLE reserva_lab_salas.reservas
    ADD CONSTRAINT FK_Reserva_Laboratorio FOREIGN KEY (laboratorio_id) REFERENCES reserva_lab_salas.laboratorios(id) ON DELETE NO ACTION;

ALTER TABLE reserva_lab_salas.reservas
    ADD CONSTRAINT FK_Reserva_Sala FOREIGN KEY (sala_id) REFERENCES reserva_lab_salas.salas(id) ON DELETE NO ACTION;

ALTER TABLE reserva_lab_salas.reservas
    ADD CONSTRAINT FK_Reserva_Status FOREIGN KEY (status_id) REFERENCES reserva_lab_salas.statuses(id) ON DELETE NO ACTION;

-- Índices úteis
CREATE INDEX IDX_Reserva_Usuario ON reserva_lab_salas.reservas(usuario_id);
CREATE INDEX IDX_Reserva_Recurso_Data ON reserva_lab_salas.reservas(laboratorio_id, sala_id, data_inicio, data_fim);
CREATE INDEX IDX_Reserva_Status ON reserva_lab_salas.reservas(status_id);

-- Inserção inicial de status sugerido
INSERT INTO reserva_lab_salas.statuses (codigo, nome) VALUES
('LIVRE', 'Livre'),
('OCUPADO', 'Ocupado'),
('BLOQUEADO', 'Bloqueado'),
('RESERVADO', 'Reservado');
