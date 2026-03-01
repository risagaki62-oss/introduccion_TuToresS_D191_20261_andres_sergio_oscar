<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Aprobaciones</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar"><h1 class="topbar-title"><i class="bi bi-check-circle me-2" style="color:var(--uts-verde)"></i>Aprobaciones</h1></div>
    <div class="page-body">
      <c:if test="${param.msg=='ok'}"><div class="alert alert-success alert-dismissible fade show"><i class="bi bi-check2 me-2"></i>Acción realizada.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
      <div class="card border-0 shadow-sm">
        <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk)">
          Solicitudes pendientes — ${pendientes.size()}
        </div>
        <div class="card-body p-0">
          <c:choose>
            <c:when test="${empty pendientes}">
              <div class="empty-box"><i class="bi bi-check-all fs-1 d-block mb-2"></i>No hay solicitudes pendientes.</div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead class="table-light"><tr><th>Nombre</th><th>Correo</th><th>Rol</th><th>Registro</th><th></th></tr></thead>
                  <tbody>
                    <c:forEach var="u" items="${pendientes}">
                      <tr>
                        <td class="fw-semibold"><c:out value="${u.nombreCompleto}"/></td>
                        <td class="text-muted small"><c:out value="${u.email}"/></td>
                        <td><span class="badge text-bg-warning">${u.rol}</span></td>
                        <td class="text-muted small">${u.fechaRegistro}</td>
                        <td>
                          <form method="post" action="${pageContext.request.contextPath}/admin/aprobaciones" class="d-inline">
                            <input type="hidden" name="userId" value="${u.id}">
                            <input type="hidden" name="accion" value="aprobar">
                            <button class="btn btn-verde btn-sm">Aprobar</button>
                          </form>
                          <form method="post" action="${pageContext.request.contextPath}/admin/aprobaciones" class="d-inline ms-1">
                            <input type="hidden" name="userId" value="${u.id}">
                            <input type="hidden" name="accion" value="rechazar">
                            <button class="btn btn-danger btn-sm">Rechazar</button>
                          </form>
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
