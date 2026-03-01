<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Ingresar</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="row g-0 min-vh-100">
  <div class="col-lg-4 d-none d-lg-flex auth-brand">
    <div>
      <div class="brand-logo mb-2">TUT<span class="verde-txt">o</span>reS</div>
      <p class="mb-4" style="opacity:.85;font-size:.95rem">Sistema de Tutorías Académicas<br>Unidades Tecnológicas de Santander</p>
      <div class="d-flex flex-column gap-2">
        <div class="fact-pill"><i class="bi bi-person-check me-2"></i>Tutores calificados disponibles</div>
        <div class="fact-pill"><i class="bi bi-house me-2"></i>60 cubículos disponibles</div>
        <div class="fact-pill"><i class="bi bi-clock me-2"></i>Horarios 7:30 — 19:00</div>
      </div>
    </div>
  </div>
  <div class="col-lg-8 d-flex align-items-center justify-content-center p-4">
    <div style="width:100%;max-width:420px">
      <div class="card border-0 shadow card-acent-azul">
        <div class="card-body p-4">
          <h2 class="fw-bold mb-1" style="color:var(--uts-azul-dk)">Bienvenido</h2>
          <p class="text-muted mb-4">Ingresa con tu correo institucional UTS</p>
          <c:if test="${not empty error}">
            <div class="alert alert-danger py-2 small"><i class="bi bi-exclamation-circle me-1"></i><c:out value="${error}"/></div>
          </c:if>
          <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="mb-3">
              <label class="form-label fw-semibold small">Correo electrónico</label>
              <input type="text" name="email" class="form-control" placeholder="usuario@uts.edu.co" required>
            </div>
            <div class="mb-4">
              <label class="form-label fw-semibold small">Contraseña</label>
              <input type="password" name="password" class="form-control" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-uts w-100 py-2 fw-semibold">
              <i class="bi bi-box-arrow-in-right me-2"></i>Ingresar
            </button>
          </form>
          <p class="text-center mt-3 mb-0 small text-muted">
            ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/registro" style="color:var(--uts-azul)">Regístrate aquí</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
</body></html>
