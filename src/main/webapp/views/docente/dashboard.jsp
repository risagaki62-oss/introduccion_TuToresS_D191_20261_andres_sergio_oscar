<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Panel Tutor</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar">
      <h1 class="topbar-title"><i class="bi bi-grid-fill me-2" style="color:var(--uts-azul)"></i>Mi Panel — Semestre ${semestre}</h1>
      <a href="${pageContext.request.contextPath}/docente/semestre" class="btn btn-uts btn-sm"><i class="bi bi-plus-lg me-1"></i>Configurar horarios</a>
    </div>
    <div class="page-body">
      <div class="row g-3 mb-4">
        <div class="col-sm-6">
          <div class="card stat-card card-acent-verde">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#d1fae5;color:var(--uts-verde)"><i class="bi bi-people-fill"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${tutorias.size()}</div><div class="text-muted small mt-1">Alumnos activos</div></div>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <div class="card stat-card card-acent-azul">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#e8f0fb;color:var(--uts-azul)"><i class="bi bi-calendar-week"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${franjas.size()}</div><div class="text-muted small mt-1">Franjas configuradas</div></div>
            </div>
          </div>
        </div>
      </div>
      <div class="card border-0 shadow-sm">
        <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk)"><i class="bi bi-list-check me-2"></i>Alumnos inscritos este semestre</div>
        <div class="card-body p-0">
          <c:choose>
            <c:when test="${empty tutorias}">
              <div class="empty-box"><i class="bi bi-inbox fs-1 d-block mb-2"></i>Aún no hay alumnos inscritos en tus franjas.</div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead class="table-light"><tr><th>Materia</th><th>Día / Hora</th><th>Cubículo</th><th>Estudiante</th><th>Tema</th></tr></thead>
                  <tbody>
                    <c:forEach var="t" items="${tutorias}">
                      <tr>
                        <td class="fw-semibold small"><c:out value="${t.franja.materia.nombre}"/></td>
                        <td class="small">${t.franja.diaSemana}<br><span class="text-muted">${t.franja.horaInicioStr}–${t.franja.horaFinStr}</span></td>
                        <td><span class="badge" style="background:var(--uts-azul)">C-${t.franja.cubiculo}</span></td>
                        <td class="small"><c:out value="${t.estudiante.usuario.nombreCompleto}"/></td>
                        <td class="text-muted small"><c:out value="${t.descripcion}"/></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>
</body></html>
