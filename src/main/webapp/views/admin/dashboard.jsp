<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar">
      <h1 class="topbar-title"><i class="bi bi-grid-fill me-2" style="color:var(--uts-azul)"></i>Dashboard</h1>
      <c:if test="${pendientes>0}">
        <a href="${pageContext.request.contextPath}/admin/aprobaciones" class="btn btn-warning btn-sm fw-semibold">
          <i class="bi bi-bell me-1"></i>${pendientes} pendiente(s)
        </a>
      </c:if>
    </div>
    <div class="page-body">
      <div class="row g-3 mb-4">
        <div class="col-sm-6 col-xl-3">
          <div class="card stat-card card-acent-azul">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#e8f0fb;color:var(--uts-azul)"><i class="bi bi-people-fill"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${conteoRoles['ESTUDIANTE'] != null ? conteoRoles['ESTUDIANTE'] : 0}</div><div class="text-muted small mt-1">Estudiantes</div></div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-xl-3">
          <div class="card stat-card card-acent-verde">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#d1fae5;color:var(--uts-verde)"><i class="bi bi-person-workspace"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${conteoRoles['TUTOR'] != null ? conteoRoles['TUTOR'] : 0}</div><div class="text-muted small mt-1">Tutores</div></div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-xl-3">
          <div class="card stat-card card-acent-verde">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#d1fae5;color:var(--uts-verde)"><i class="bi bi-check2-circle"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${conteoTutorias['ACTIVA'] != null ? conteoTutorias['ACTIVA'] : 0}</div><div class="text-muted small mt-1">Tutorías activas</div></div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-xl-3">
          <div class="card stat-card" style="border-top:4px solid #f59e0b">
            <div class="card-body d-flex align-items-center gap-3">
              <div class="stat-icon" style="background:#fff4e6;color:#e76f00"><i class="bi bi-hourglass-split"></i></div>
              <div><div class="fs-2 fw-bold lh-1" style="color:var(--uts-azul-dk)">${pendientes}</div><div class="text-muted small mt-1">Pendientes</div></div>
            </div>
          </div>
        </div>
      </div>
      <div class="row g-3">
        <div class="col-md-6">
          <div class="card border-0 shadow-sm h-100">
            <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk);border-bottom:2px solid var(--uts-azul)"><i class="bi bi-book me-2"></i>Top Materias</div>
            <div class="card-body">
              <c:choose>
                <c:when test="${empty topMaterias}"><p class="text-muted mb-0 small">Sin datos aún.</p></c:when>
                <c:otherwise>
                  <c:forEach var="m" items="${topMaterias}">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                      <span class="small"><c:out value="${m[0]}"/></span>
                      <span class="badge" style="background:var(--uts-verde)">${m[1]}</span>
                    </div>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="card border-0 shadow-sm h-100">
            <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk);border-bottom:2px solid var(--uts-azul)"><i class="bi bi-trophy me-2"></i>Top Tutores</div>
            <div class="card-body">
              <c:choose>
                <c:when test="${empty topDocentes}"><p class="text-muted mb-0 small">Sin datos aún.</p></c:when>
                <c:otherwise>
                  <c:forEach var="t" items="${topDocentes}">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                      <span class="small"><c:out value="${t[0]}"/></span>
                      <span class="badge" style="background:var(--uts-azul)">${t[1]}</span>
                    </div>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body></html>
