<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Materias</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar"><h1 class="topbar-title"><i class="bi bi-book-fill me-2" style="color:var(--uts-azul)"></i>Gestión de Materias</h1></div>
    <div class="page-body">
      <c:if test="${param.msg=='ok'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Cambio guardado.</div></c:if>
      <div class="row g-3">
        <div class="col-md-5">
          <div class="card border-0 shadow-sm card-acent-azul">
            <div class="card-header bg-white fw-bold small">Nueva materia</div>
            <div class="card-body">
              <form method="post" action="${pageContext.request.contextPath}/admin/materias">
                <input type="hidden" name="accion" value="crear">
                <div class="mb-3"><label class="form-label fw-semibold small">Nombre *</label><input type="text" name="nombre" class="form-control form-control-sm" required></div>
                <div class="mb-3"><label class="form-label fw-semibold small">Descripción</label><textarea name="descripcion" class="form-control form-control-sm" rows="3"></textarea></div>
                <button class="btn btn-uts btn-sm w-100">Guardar</button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-md-7">
          <div class="card border-0 shadow-sm">
            <div class="card-header bg-white fw-bold small">Todas las materias (${materias.size()})</div>
            <div class="table-responsive" style="max-height:480px;overflow-y:auto">
              <table class="table table-hover mb-0">
                <thead class="table-light"><tr><th>Nombre</th><th>Estado</th><th></th></tr></thead>
                <tbody>
                  <c:forEach var="m" items="${materias}">
                    <tr>
                      <td><div class="fw-semibold small"><c:out value="${m.nombre}"/></div><div class="text-muted" style="font-size:.78rem"><c:out value="${m.descripcion}"/></div></td>
                      <td><span class="badge ${m.activo?'text-bg-success':'text-bg-secondary'}">${m.activo?'Activa':'Inactiva'}</span></td>
                      <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/materias">
                          <input type="hidden" name="accion" value="toggle">
                          <input type="hidden" name="materiaId" value="${m.id}">
                          <button class="btn btn-outline-secondary btn-sm">${m.activo?'Desactivar':'Activar'}</button>
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body></html>
