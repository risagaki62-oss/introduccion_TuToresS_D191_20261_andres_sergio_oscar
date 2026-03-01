<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Buscar Tutoría</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar"><h1 class="topbar-title"><i class="bi bi-search me-2" style="color:var(--uts-azul)"></i>Buscar Tutoría</h1></div>
    <div class="page-body">
      <c:if test="${param.error=='llena'}"><div class="alert alert-danger small"><i class="bi bi-x-circle me-1"></i>Esa franja ya no tiene cupos disponibles.</div></c:if>
      <c:if test="${param.error=='inscrito'}"><div class="alert alert-warning small"><i class="bi bi-exclamation me-1"></i>Ya estás inscrito en esa franja.</div></c:if>

      <!-- Filtro -->
      <div class="card border-0 shadow-sm mb-4 card-acent-azul">
        <div class="card-body">
          <form method="get" action="${pageContext.request.contextPath}/estudiante/buscar" class="d-flex gap-2 align-items-end flex-wrap">
            <div class="flex-grow-1" style="min-width:220px">
              <label class="form-label fw-semibold small mb-1">Selecciona una materia</label>
              <select name="materiaId" class="form-select form-select-sm" required>
                <option value="">Elige una materia…</option>
                <c:forEach var="m" items="${materias}">
                  <option value="${m.id}" ${m.id == materiaSeleccionada ? 'selected' : ''}>
                    <c:out value="${m.nombre}"/>
                  </option>
                </c:forEach>
              </select>
            </div>
            <button class="btn btn-uts btn-sm"><i class="bi bi-search me-1"></i>Buscar</button>
          </form>
        </div>
      </div>

      <!-- Resultados -->
      <c:if test="${not empty resultados}">
        <c:forEach var="grupo" items="${resultados}">
          <div class="card border-0 shadow-sm mb-3">
            <div class="card-header bg-white d-flex justify-content-between align-items-center">
              <div>
                <div class="fw-bold" style="color:var(--uts-azul-dk)">
                  <i class="bi bi-person-workspace me-2"></i>
                  <c:out value="${not empty grupo.docente.titulo ? grupo.docente.titulo.concat(' ') : ''}${grupo.docente.usuario.nombreCompleto}"/>
                </div>
              </div>
              <span class="badge text-bg-success">${grupo.franjas.size()} franja(s)</span>
            </div>
            <div class="card-body pb-2">
              <p class="text-muted small mb-2">Haz clic en un horario para reservar:</p>
              <c:forEach var="f" items="${grupo.franjas}">
                <a href="${pageContext.request.contextPath}/estudiante/reservar?franjaId=${f.id}" class="slot-chip">
                  <i class="bi bi-calendar me-1"></i>${f.diaSemana}
                  <i class="bi bi-clock ms-2 me-1"></i>${f.horaInicioStr}–${f.horaFinStr}
                  <i class="bi bi-house ms-2 me-1"></i>C-${f.cubiculo}
                  <span class="ms-2 badge text-bg-success">${f.cuposLibres} cupo(s)</span>
                </a>
              </c:forEach>
            </div>
          </div>
        </c:forEach>
      </c:if>

      <c:if test="${not empty materiaSeleccionada and empty resultados}">
        <div class="card border-0 shadow-sm">
          <div class="empty-box"><i class="bi bi-calendar-x fs-1 d-block mb-2"></i>No hay franjas disponibles para esa materia en el semestre actual.</div>
        </div>
      </c:if>
    </div>
  </div>
</div>
</body></html>
