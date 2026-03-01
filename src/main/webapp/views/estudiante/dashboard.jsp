<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Mi Panel</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar">
      <h1 class="topbar-title"><i class="bi bi-grid-fill me-2" style="color:var(--uts-azul)"></i>Mis Tutorías</h1>
      <a href="${pageContext.request.contextPath}/estudiante/buscar" class="btn btn-uts btn-sm"><i class="bi bi-search me-1"></i>Buscar tutoría</a>
    </div>
    <div class="page-body">
      <c:if test="${param.msg=='reservada'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>¡Tutoría reservada con éxito!</div></c:if>
      <c:if test="${param.msg=='cancelada'}"><div class="alert alert-info small"><i class="bi bi-info-circle me-1"></i>Tutoría cancelada.</div></c:if>
      <c:choose>
        <c:when test="${empty tutorias}">
          <div class="card border-0 shadow-sm">
            <div class="empty-box">
              <i class="bi bi-calendar-x fs-1 d-block mb-2"></i>
              No tienes tutorías activas.<br>
              <a href="${pageContext.request.contextPath}/estudiante/buscar" style="color:var(--uts-azul)">Busca una tutoría</a>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="row g-3">
            <c:forEach var="t" items="${tutorias}">
              <div class="col-md-6 col-xl-4">
                <div class="card border-0 shadow-sm h-100 card-acent-verde">
                  <div class="card-body">
                    <h6 class="fw-bold mb-3" style="color:var(--uts-azul-dk)"><c:out value="${t.franja.materia.nombre}"/></h6>
                    <div class="d-flex flex-column gap-1 mb-3">
                      <div class="small text-muted"><i class="bi bi-calendar-week me-2"></i>${t.franja.diaSemana} · ${t.franja.horaInicioStr}–${t.franja.horaFinStr}</div>
                      <div class="small text-muted"><i class="bi bi-house me-2"></i>Cubículo C-${t.franja.cubiculo}</div>
                      <div class="small text-muted"><i class="bi bi-person-workspace me-2"></i>
                        <c:out value="${not empty t.franja.docente.titulo ? t.franja.docente.titulo.concat(' ') : ''}${t.franja.docente.usuario.nombreCompleto}"/>
                      </div>
                    </div>
                    <div class="small text-muted fst-italic mb-3 p-2 rounded" style="background:#f8f9fa">
                      "<c:out value="${t.descripcion}"/>"
                    </div>
                    <form method="post" action="${pageContext.request.contextPath}/estudiante/cancelar"
                          onsubmit="return confirm('¿Cancelar esta tutoría?')">
                      <input type="hidden" name="tutoriaId" value="${t.id}">
                      <button class="btn btn-danger btn-sm w-100"><i class="bi bi-x-circle me-1"></i>Cancelar</button>
                    </form>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
</body></html>
