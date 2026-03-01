<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Usuarios</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar"><h1 class="topbar-title"><i class="bi bi-people-fill me-2" style="color:var(--uts-azul)"></i>Gestión de Usuarios</h1></div>
    <div class="page-body">
      <c:if test="${not empty errorCrear}"><div class="alert alert-danger small"><c:out value="${errorCrear}"/></div></c:if>
      <c:if test="${param.msg=='creado'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Usuario creado.</div></c:if>
      <c:if test="${param.msg=='eliminado'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Usuario eliminado.</div></c:if>
      <c:if test="${param.msg=='ok'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Cambio realizado.</div></c:if>
      <div class="row g-3">
        <div class="col-lg-4">
          <div class="card border-0 shadow-sm card-acent-azul">
            <div class="card-header bg-white fw-bold small">Crear usuario</div>
            <div class="card-body">
              <form method="post" action="${pageContext.request.contextPath}/admin/usuarios">
                <input type="hidden" name="accion" value="crear">
                <div class="row g-2 mb-2">
                  <div class="col"><label class="form-label fw-semibold small mb-1">Nombre *</label><input type="text" name="nombre" class="form-control form-control-sm" required></div>
                  <div class="col"><label class="form-label fw-semibold small mb-1">Apellido</label><input type="text" name="apellido" class="form-control form-control-sm"></div>
                </div>
                <div class="mb-2"><label class="form-label fw-semibold small mb-1">Correo *</label><input type="email" name="email" class="form-control form-control-sm" required></div>
                <div class="mb-2"><label class="form-label fw-semibold small mb-1">Contraseña *</label><input type="text" name="password" class="form-control form-control-sm" required></div>
                <div class="mb-2">
                  <label class="form-label fw-semibold small mb-1">Rol *</label>
                  <select name="rol" class="form-select form-select-sm" id="rolAdm" onchange="tgTit()">
                    <option value="ESTUDIANTE">Estudiante</option>
                    <option value="TUTOR">Tutor</option>
                    <option value="ADMIN">Admin</option>
                  </select>
                </div>
                <div class="mb-3 d-none" id="titAdm"><label class="form-label fw-semibold small mb-1">Título</label><input type="text" name="titulo" class="form-control form-control-sm"></div>
                <button class="btn btn-uts btn-sm w-100">Crear usuario</button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-lg-8">
          <div class="card border-0 shadow-sm">
            <div class="card-header bg-white fw-bold small">Todos los usuarios (${usuarios.size()})</div>
            <div class="table-responsive" style="max-height:520px;overflow-y:auto">
              <table class="table table-hover mb-0">
                <thead class="table-light"><tr><th>Nombre</th><th>Rol</th><th>Estado</th><th></th></tr></thead>
                <tbody>
                  <c:forEach var="u" items="${usuarios}">
                    <tr>
                      <td><div class="fw-semibold small"><c:out value="${u.nombreCompleto}"/></div><div class="text-muted" style="font-size:.75rem"><c:out value="${u.email}"/></div></td>
                      <td><span class="badge" style="background:var(--uts-azul)">${u.rol}</span></td>
                      <td><span class="badge ${u.estado=='APROBADO'?'text-bg-success':u.estado=='PENDIENTE'?'text-bg-warning':'text-bg-secondary'}">${u.estado}</span></td>
                      <td>
                        <div class="d-flex gap-1 flex-wrap">
                          <c:if test="${u.estado!='APROBADO'}">
                            <form method="post" action="${pageContext.request.contextPath}/admin/usuarios">
                              <input type="hidden" name="accion" value="activar"><input type="hidden" name="userId" value="${u.id}">
                              <button class="btn btn-verde btn-sm">Activar</button>
                            </form>
                          </c:if>
                          <c:if test="${u.estado=='APROBADO'}">
                            <form method="post" action="${pageContext.request.contextPath}/admin/usuarios">
                              <input type="hidden" name="accion" value="desactivar"><input type="hidden" name="userId" value="${u.id}">
                              <button class="btn btn-outline-secondary btn-sm">Desactivar</button>
                            </form>
                          </c:if>
                          <form method="post" action="${pageContext.request.contextPath}/admin/usuarios" onsubmit="return confirm('¿Eliminar usuario?')">
                            <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="userId" value="${u.id}">
                            <button class="btn btn-danger btn-sm">Eliminar</button>
                          </form>
                        </div>
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
<script>function tgTit(){document.getElementById('titAdm').classList.toggle('d-none',document.getElementById('rolAdm').value!=='TUTOR');}</script>
</body></html>
