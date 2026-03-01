-- ══════════════════════════════════════════════════════════════════
-- TUToreS v3 — Esquema PostgreSQL
-- Unidades Tecnológicas de Santander
-- ══════════════════════════════════════════════════════════════════

DROP TABLE IF EXISTS tutorias        CASCADE;
DROP TABLE IF EXISTS franja_horaria  CASCADE;
DROP TABLE IF EXISTS estudiantes     CASCADE;
DROP TABLE IF EXISTS docentes        CASCADE;
DROP TABLE IF EXISTS materias        CASCADE;
DROP TABLE IF EXISTS usuarios        CASCADE;

-- ── Usuarios ───────────────────────────────────────────────────────
CREATE TABLE usuarios (
    id             SERIAL PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    apellido       VARCHAR(100) NOT NULL,
    email          VARCHAR(150) UNIQUE NOT NULL,
    password       VARCHAR(100) NOT NULL,
    rol            VARCHAR(20)  NOT NULL CHECK (rol IN ('ADMIN','TUTOR','ESTUDIANTE')),
    estado         VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
                   CHECK (estado IN ('PENDIENTE','APROBADO','RECHAZADO','INACTIVO')),
    fecha_registro TIMESTAMP DEFAULT NOW()
);

-- ── Perfiles ───────────────────────────────────────────────────────
CREATE TABLE estudiantes (
    id         SERIAL PRIMARY KEY,
    usuario_id INTEGER UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE docentes (
    id         SERIAL PRIMARY KEY,
    usuario_id INTEGER UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    titulo     VARCHAR(150)
);

-- ── Catálogo de materias ───────────────────────────────────────────
CREATE TABLE materias (
    id          SERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(300),
    activo      BOOLEAN NOT NULL DEFAULT TRUE
);

-- ── Franjas horarias ──────────────────────────────────────────────
-- Una franja = tutor + materia + día + hora + cubículo (todo el semestre)
CREATE TABLE franja_horaria (
    id              SERIAL PRIMARY KEY,
    docente_id      INTEGER     NOT NULL REFERENCES docentes(id) ON DELETE CASCADE,
    materia_id      INTEGER     NOT NULL REFERENCES materias(id),
    dia_semana      VARCHAR(10) NOT NULL
                    CHECK (dia_semana IN ('LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADO')),
    hora_inicio     TIME        NOT NULL,
    semestre        VARCHAR(10) NOT NULL,
    cubiculo        INTEGER     NOT NULL CHECK (cubiculo BETWEEN 1 AND 60),
    max_estudiantes INTEGER     NOT NULL DEFAULT 3 CHECK (max_estudiantes BETWEEN 1 AND 10),
    -- Un cubículo no puede tener dos tutores al mismo tiempo
    UNIQUE (dia_semana, hora_inicio, semestre, cubiculo)
);

-- ── Reservas ──────────────────────────────────────────────────────
CREATE TABLE tutorias (
    id            SERIAL PRIMARY KEY,
    franja_id     INTEGER NOT NULL REFERENCES franja_horaria(id),
    estudiante_id INTEGER NOT NULL REFERENCES estudiantes(id),
    descripcion   TEXT    NOT NULL,
    estado        VARCHAR(15) NOT NULL DEFAULT 'ACTIVA'
                  CHECK (estado IN ('ACTIVA','COMPLETADA','CANCELADA')),
    fecha_reserva TIMESTAMP DEFAULT NOW(),
    UNIQUE (franja_id, estudiante_id)
);

-- ── Vista de disponibilidad ────────────────────────────────────────
CREATE OR REPLACE VIEW v_franja_disp AS
SELECT
    f.id,
    f.docente_id,
    f.materia_id,
    f.dia_semana,
    f.hora_inicio,
    f.semestre,
    f.cubiculo,
    f.max_estudiantes,
    m.nombre                                        AS materia_nombre,
    u.nombre                                        AS docente_nombre,
    u.apellido                                      AS docente_apellido,
    d.titulo                                        AS docente_titulo,
    COUNT(t.id)                                     AS inscritos,
    f.max_estudiantes - COUNT(t.id)                 AS cupos_libres,
    CASE WHEN COUNT(t.id) >= f.max_estudiantes
         THEN 'LLENA' ELSE 'DISPONIBLE' END         AS disponibilidad
FROM franja_horaria f
JOIN materias  m ON m.id = f.materia_id
JOIN docentes  d ON d.id = f.docente_id
JOIN usuarios  u ON u.id = d.usuario_id
LEFT JOIN tutorias t ON t.franja_id = f.id AND t.estado = 'ACTIVA'
GROUP BY f.id, m.nombre, u.nombre, u.apellido, d.titulo;
