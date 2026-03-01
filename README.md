# TUToreS v2.0 — Sistema de Tutorías Académicas UTS

**Unidades Tecnológicas de Santander**  
Stack: Java 21 + Servlets + JSP + Bootstrap 5 + PostgreSQL · XAMPP Tomcat 9

---

## Configuración rápida (XAMPP local)

### 1. Requisitos previos
- Java 21 (JDK)
- Apache Maven 3.8+
- XAMPP con Tomcat 9 y PostgreSQL
- PostgreSQL corriendo en `localhost:5432`

### 2. Crear la base de datos
```sql
CREATE DATABASE "TUToreS";
```

### 3. Ejecutar el esquema
```bash
psql -U postgres -d TUToreS -f sql/01_schema.sql
psql -U postgres -d TUToreS -f sql/02_seed.sql
```

### 4. Compilar y desplegar
```bash
mvn clean package -DskipTests
copy target\tutores.war C:\xampp\tomcat\webapps\
```

### 5. Iniciar Tomcat
Desde el Panel de XAMPP → Start → Tomcat

### 6. Acceder a la aplicación
```
http://localhost:8080/tutores
```

---

## Credenciales por defecto

| Rol          | Email              | Contraseña |
|--------------|--------------------|-----------|
| Administrador | admin@admin       | admin      |

> **El usuario admin se crea automáticamente** al iniciar Tomcat por primera vez
> a través del `DatabaseInitializer`. Si necesitas restablecer la contraseña,
> ejecuta `AdminPasswordReset.java` y copia el SQL impreso en la consola.

---

## Registro de nuevos usuarios

| Rol         | Dominio de correo obligatorio   |
|-------------|----------------------------------|
| Estudiante  | `@uts.edu.co`                    |
| Tutor       | `@correo.uts.edu.co`             |

> El administrador puede crear usuarios con cualquier correo desde el panel.
> Las cuentas auto-registradas quedan **PENDIENTE** hasta aprobación del admin.

---

## Modelo de franjas horarias

- **Una franja = materia + día + hora + cubículo + máximo estudiantes**
- Horarios disponibles: **07:30 – 19:00**, sesiones de **44 minutos**
- **60 cubículos** (un solo tutor por cubículo por franja/hora)
- Un tutor puede dictar distintas materias en distintos días y horas
- Un estudiante puede reservar cualquier franja con cupos disponibles

---

## Estructura del proyecto

```
tutores/
├── pom.xml                   ← Maven (Java 21, Servlet 4.0, WAR)
├── sql/
│   ├── 01_schema.sql         ← Tablas + vista v_franja_disponibilidad
│   └── 02_seed.sql           ← Materias iniciales
└── src/main/
    ├── java/com/tutores/
    │   ├── config/
    │   │   ├── DBConnection.java
    │   │   └── DatabaseInitializer.java  ← Crea admin al arrancar
    │   ├── model/            ← POJOs (Usuario, Docente, Estudiante, Materia,
    │   │                        FranjaHoraria, Tutoria)
    │   ├── dao/              ← Acceso a datos con PreparedStatements
    │   ├── servlet/
    │   │   ├── auth/         ← Login, Logout, Registro
    │   │   ├── admin/        ← Dashboard, Aprobaciones, Usuarios, Materias
    │   │   ├── docente/      ← Dashboard, ConfigSemestre, Historial
    │   │   └── estudiante/   ← Dashboard, Buscar, Reservar, Cancelar, Historial
    │   └── util/
    │       ├── PasswordUtil.java
    │       ├── SemestreUtil.java    ← Calcula semestre actual (YYYY-1 o YYYY-2)
    │       ├── SessionFilter.java  ← Auth + RBAC
    │       └── AdminPasswordReset.java
    └── webapp/
        ├── WEB-INF/web.xml
        ├── index.jsp
        ├── views/
        │   ├── auth/          ← login.jsp, registro.jsp
        │   ├── admin/         ← dashboard.jsp, aprobaciones.jsp, usuarios.jsp, materias.jsp
        │   ├── docente/       ← dashboard.jsp, configSemestre.jsp, historial.jsp
        │   ├── estudiante/    ← dashboard.jsp, buscarTutoria.jsp, reservar.jsp, historial.jsp
        │   ├── includes/sidebar.jsp
        │   └── error.jsp
        └── static/css/tutores.css
```

---

## Problemas frecuentes

| Problema | Solución |
|----------|----------|
| Login admin falla | Ejecutar `AdminPasswordReset.java` para regenerar el hash |
| Error de conexión BD | Verificar PostgreSQL en `localhost:5432` con BD `TUToreS` |
| 404 en todas las rutas | Verificar que el WAR esté desplegado en Tomcat |
| Logs de error | Revisar `C:\xampp\tomcat\logs\catalina.out` |
