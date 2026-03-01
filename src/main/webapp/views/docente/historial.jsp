<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Historial Tutor</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar"><h1 class="topbar-title"><i class="bi bi-clock-history me-2" style="color:var(--uts-azul)"></i>Historial de tutorías</h1></div>
    <div class="page-body">
      <div class="card border-0 shadow-sm">
        <div class="card-body p-0">
          <c:choose>
            <c:when test="${empty historial}">
              <div class="empty-box"><i class="bi bi-clock fs-1 d-block mb-2"></i>Sin tutorías anteriores.</div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead class="table-light"><tr><th>Materia</th><th>Día / Hora</th><th>Cubículo</th><th>Estudiante</th><th>Estado</th></tr></thead>
                  <tbody>
                    <c:forEach var="t" items="${historial}">
                      <tr>
                        <td class="small"><c:out value="${t.franja.materia.nombre}"/></td>
                        <td class="small">${t.franja.diaSemana} ${t.franja.horaInicioStr}</td>
                        <td class="small">C-${t.franja.cubiculo}</td>
                        <td class="small"><c:out value="${t.estudiante.usuario.nombreCompleto}"/></td>
                        <td>
                          <span class="badge ${t.estado=='ACTIVA'?'text-bg-success':t.estado=='COMPLETADA'?'text-bg-primary':'text-bg-danger'}">${t.estado}</span>
                        </td>
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
