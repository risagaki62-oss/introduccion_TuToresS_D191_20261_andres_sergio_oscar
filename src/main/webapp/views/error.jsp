<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Error</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light d-flex align-items-center justify-content-center min-vh-100">
<div class="text-center p-4">
  <div style="font-size:5rem;font-weight:800;color:var(--uts-azul-dk)">${pageContext.errorData.statusCode}</div>
  <p class="text-muted mb-4">Ha ocurrido un error inesperado.</p>
  <a href="${pageContext.request.contextPath}/login" class="btn btn-uts">
    <i class="bi bi-house me-2"></i>Ir al inicio
  </a>
</div>
</body></html>
