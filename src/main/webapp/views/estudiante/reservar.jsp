<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Confirmar Reserva</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar">
      <h1 class="topbar-title"><i class="bi bi-calendar-plus me-2" style="color:var(--uts-verde)"></i>Confirmar Reserva</h1>
      <a href="${pageContext.request.contextPath}/estudiante/buscar" class="btn btn-outline-secondary btn-sm">
        <i class="bi bi-arrow-left me-1"></i>Volver
      </a>
    </div>
    <div class="page-body d-flex justify-content-center">
      <div style="width:100%;max-width:540px">
        <div class="card border-0 shadow card-acent-verde">
          <div class="card-header bg-white fw-bold" style="color:var(--uts-azul-dk)">
            <i class="bi bi-book me-2"></i><c:out value="${franja.materia.nombre}"/>
          </div>
          <div class="card-body">
            <div class="row g-2 mb-4 p-3 rounded" style="background:#f8f9fa">
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Tutor</div>
                <div class="fw-semibold small">
                  <c:out value="${not empty franja.docente.titulo ? franja.docente.titulo.concat(' ') : ''}${franja.docente.usuario.nombreCompleto}"/>
                </div>
              </div>
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Día</div>
                <div class="fw-semibold small">${franja.diaSemana}</div>
              </div>
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Horario</div>
                <div class="fw-semibold small">${franja.horaInicioStr} – ${franja.horaFinStr}</div>
              </div>
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Cubículo</div>
                <div class="fw-semibold small"><span class="badge" style="background:var(--uts-azul)">C-${franja.cubiculo}</span></div>
              </div>
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Cupos libres</div>
                <div class="fw-semibold small"><span class="badge text-bg-success">${franja.cuposLibres} de ${franja.maxEstudiantes}</span></div>
              </div>
              <div class="col-6">
                <div class="text-muted" style="font-size:.72rem;text-transform:uppercase;font-weight:700">Semestre</div>
                <div class="fw-semibold small">${franja.semestre}</div>
              </div>
            </div>

            <div class="alert alert-info small py-2 mb-3">
              <i class="bi bi-info-circle me-1"></i>
              Esta sesión se repetirá todos los <strong>${franja.diaSemana}</strong> a las <strong>${franja.horaInicioStr}</strong> durante todo el semestre.
            </div>

            <form method="post" action="${pageContext.request.contextPath}/estudiante/reservar">
              <input type="hidden" name="franjaId" value="${franja.id}">
              <div class="mb-3">
                <label class="form-label fw-semibold small">¿En qué necesitas ayuda? *</label>
                <textarea name="descripcion" class="form-control" rows="4"
                          placeholder="Describe el tema o dificultad que quieres trabajar con el tutor…"
                          minlength="5" maxlength="500" required></textarea>
              </div>
              <button type="submit" class="btn btn-verde w-100 py-2 fw-semibold">
                <i class="bi bi-check-circle me-2"></i>Confirmar reserva
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body></html>
