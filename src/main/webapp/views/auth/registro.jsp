<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Registro</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="row g-0 min-vh-100">
  <div class="col-lg-4 d-none d-lg-flex auth-brand">
    <div>
      <div class="brand-logo mb-2">TUT<span class="verde-txt">o</span>reS</div>
      <p style="opacity:.85">Crea tu cuenta institucional para acceder al sistema de tutorías</p>
    </div>
  </div>
  <div class="col-lg-8 d-flex align-items-center justify-content-center p-4">
    <div style="width:100%;max-width:480px">
      <div class="card border-0 shadow card-acent-azul">
        <div class="card-body p-4">
          <h2 class="fw-bold mb-1" style="color:var(--uts-azul-dk)">Crear cuenta</h2>
          <p class="text-muted mb-4">Completa tus datos con tu correo UTS</p>
          <c:if test="${not empty error}"><div class="alert alert-danger py-2 small"><c:out value="${error}"/></div></c:if>
          <c:if test="${not empty exito}"><div class="alert alert-success py-2 small"><c:out value="${exito}"/></div></c:if>
          <form method="post" action="${pageContext.request.contextPath}/registro">
            <div class="row g-2 mb-3">
              <div class="col"><label class="form-label fw-semibold small">Nombre *</label><input type="text" name="nombre" class="form-control" required></div>
              <div class="col"><label class="form-label fw-semibold small">Apellido *</label><input type="text" name="apellido" class="form-control" required></div>
            </div>
            <div class="mb-3">
              <label class="form-label fw-semibold small">Tipo de cuenta *</label>
              <select name="rol" class="form-select" id="rolSel" onchange="upd()">
                <option value="">Selecciona…</option>
                <option value="ESTUDIANTE">Estudiante</option>
                <option value="TUTOR">Tutor / Monitor</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label fw-semibold small">Correo institucional *</label>
              <input type="email" name="email" id="emailIn" class="form-control" placeholder="usuario@uts.edu.co" required>
              <div class="form-text" id="emailHint"></div>
            </div>
            <div class="mb-3 d-none" id="titDiv">
              <label class="form-label fw-semibold small">Título / Cargo</label>
              <input type="text" name="titulo" class="form-control" placeholder="Ej: Mg. Ingeniería de Sistemas">
            </div>
            <div class="mb-4">
              <label class="form-label fw-semibold small">Contraseña *</label>
              <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-uts w-100 py-2 fw-semibold">Registrarse</button>
          </form>
          <p class="text-center mt-3 mb-0 small text-muted">
            ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login" style="color:var(--uts-azul)">Inicia sesión</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
function upd(){
  const r=document.getElementById('rolSel').value,h=document.getElementById('emailHint'),
        e=document.getElementById('emailIn'),t=document.getElementById('titDiv');
  if(r==='ESTUDIANTE'){h.textContent='Formato: juan.perez@uts.edu.co';e.placeholder='juan.perez@uts.edu.co';t.classList.add('d-none');}
  else if(r==='TUTOR'){h.textContent='Formato: nombre.apellido@correo.uts.edu.co';e.placeholder='nombre.apellido@correo.uts.edu.co';t.classList.remove('d-none');}
  else{h.textContent='';t.classList.add('d-none');}
}
</script>
</body></html>
