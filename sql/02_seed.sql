-- ══════════════════════════════════════════════════════════════════
-- TUToreS v3 — Datos iniciales
-- Admin: admin@admin / admin  (contraseña en texto plano)
-- ══════════════════════════════════════════════════════════════════

INSERT INTO usuarios (nombre, apellido, email, password, rol, estado)
VALUES ('Administrador', 'UTS', 'admin@admin', 'admin', 'ADMIN', 'APROBADO')
ON CONFLICT (email) DO NOTHING;

-- Materias UTS — Ingeniería de Sistemas
INSERT INTO materias (nombre, descripcion) VALUES
  ('Cálculo Diferencial',       'Límites, derivadas y aplicaciones'),
  ('Cálculo Integral',          'Integrales definidas e indefinidas'),
  ('Álgebra Lineal',            'Vectores, matrices y espacios vectoriales'),
  ('Programación I',            'Fundamentos de programación en Java'),
  ('Programación II',           'Estructuras de datos y algoritmos'),
  ('Base de Datos',             'SQL, modelado relacional y normalización'),
  ('Física I',                  'Mecánica clásica: cinemática y dinámica'),
  ('Física II',                 'Electromagnetismo y ondas'),
  ('Estadística',               'Probabilidad y estadística descriptiva'),
  ('Inglés Técnico',            'Lectura y escritura técnica en inglés'),
  ('Redes de Computadores',     'Protocolos TCP/IP y arquitectura de redes'),
  ('Sistemas Operativos',       'Procesos, memoria y sistemas de archivos'),
  ('Ecuaciones Diferenciales',  'Métodos analíticos y numéricos'),
  ('Lógica Matemática',         'Proposiciones, conjuntos y predicados')
ON CONFLICT DO NOTHING;
