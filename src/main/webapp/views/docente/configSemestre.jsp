<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>TUToreS — Mis Horarios</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tutores.css">
</head><body class="bg-light">
<div class="d-flex">
  <jsp:include page="/views/includes/sidebar.jsp"/>
  <div class="main-wrap flex-grow-1">
    <div class="topbar">
      <h1 class="topbar-title"><i class="bi bi-calendar-week me-2" style="color:var(--uts-azul)"></i>Mis Horarios — ${semestre}</h1>
    </div>
    <div class="page-body">

      <c:if test="${not empty errorFranja}">
        <div class="alert alert-danger alert-dismissible fade show small">
          <i class="bi bi-exclamation-circle me-1"></i><c:out value="${errorFranja}"/>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.msg=='ok'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Franja agregada correctamente.</div></c:if>
      <c:if test="${param.msg=='eliminada'}"><div class="alert alert-success small"><i class="bi bi-check2 me-1"></i>Franja eliminada.</div></c:if>

      <div class="row g-3">
        <!-- ── FORMULARIO ── -->
        <div class="col-lg-5">
          <div class="card border-0 shadow-sm card-acent-azul">
            <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk)">
              <i class="bi bi-plus-circle me-2"></i>Agregar franja horaria
            </div>
            <div class="card-body">
              <form method="post" action="${pageContext.request.contextPath}/docente/semestre">
                <input type="hidden" name="accion" value="agregarFranja">

                <div class="mb-3">
                  <label class="form-label fw-semibold small">Materia *</label>
                  <select name="materiaId" class="form-select form-select-sm" required>
                    <option value="">Selecciona materia…</option>
                    <c:forEach var="m" items="${materias}">
                      <option value="${m.id}"><c:out value="${m.nombre}"/></option>
                    </c:forEach>
                  </select>
                </div>

                <div class="row g-2 mb-3">
                  <div class="col-6">
                    <label class="form-label fw-semibold small">Día *</label>
                    <%-- CORRECTO: opciones HTML directas, sin EL list literal que JSTL 1.2 no soporta --%>
                    <select name="dia" id="diaSelect" class="form-select form-select-sm" required onchange="cargarCubiculos()">
                      <option value="">Día…</option>
                      <option value="LUNES">LUNES</option>
                      <option value="MARTES">MARTES</option>
                      <option value="MIERCOLES">MIÉRCOLES</option>
                      <option value="JUEVES">JUEVES</option>
                      <option value="VIERNES">VIERNES</option>
                      <option value="SABADO">SÁBADO</option>
                    </select>
                  </div>
                  <div class="col-6">
                    <label class="form-label fw-semibold small">Hora inicio *</label>
                    <select name="hora" id="horaSelect" class="form-select form-select-sm" required onchange="cargarCubiculos()">
                      <option value="">Hora…</option>
                      <c:forEach var="slot" items="${timeSlots}">
                        <option value="${slot}">${slot}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>

                <div class="mb-3">
                  <label class="form-label fw-semibold small">Máx. estudiantes *</label>
                  <input type="number" name="maxEstudiantes" class="form-control form-control-sm" min="1" max="10" value="3" required>
                </div>

                <!-- Mapa de cubículos -->
                <div class="mb-3">
                  <label class="form-label fw-semibold small">Cubículo * <span class="text-muted fw-normal" id="cubStatus">(selecciona día y hora primero)</span></label>
                  <div class="cubiculo-grid mb-2" id="cubGrid"></div>
                  <input type="hidden" name="cubiculo" id="cubInput">
                  <div id="cubLeyenda" class="d-flex gap-3 mt-2 d-none" style="font-size:.75rem">
                    <span><span class="d-inline-block me-1" style="width:14px;height:14px;background:#fff;border:1.5px solid #dee2e6;border-radius:3px;vertical-align:middle"></span>Libre</span>
                    <span><span class="d-inline-block me-1" style="width:14px;height:14px;background:#003F8A;border-radius:3px;vertical-align:middle"></span>Seleccionado</span>
                    <span><span class="d-inline-block me-1" style="width:14px;height:14px;background:#f0f0f0;border-radius:3px;vertical-align:middle"></span>Ocupado</span>
                    <span><span class="d-inline-block me-1" style="width:14px;height:14px;background:#d1fae5;border-radius:3px;vertical-align:middle"></span>Tuyo</span>
                  </div>
                </div>

                <button type="submit" class="btn btn-uts w-100" onclick="return chkCub()">
                  <i class="bi bi-plus-lg me-1"></i>Agregar franja
                </button>
              </form>
            </div>
          </div>
        </div>

        <!-- ── MIS FRANJAS ── -->
        <div class="col-lg-7">
          <div class="card border-0 shadow-sm">
            <div class="card-header bg-white fw-bold small" style="color:var(--uts-azul-dk)">
              <i class="bi bi-list-check me-2"></i>Mis franjas este semestre (${misFranjas.size()})
            </div>
            <div class="card-body p-0">
              <c:choose>
                <c:when test="${empty misFranjas}">
                  <div class="empty-box"><i class="bi bi-calendar-x fs-1 d-block mb-2"></i>Aún no has configurado franjas para este semestre.</div>
                </c:when>
                <c:otherwise>
                  <div class="table-responsive">
                    <table class="table table-hover mb-0">
                      <thead class="table-light"><tr><th>Materia</th><th>Día / Hora</th><th>Cubículo</th><th>Cupos</th><th></th></tr></thead>
                      <tbody>
                        <c:forEach var="f" items="${misFranjas}">
                          <tr>
                            <td class="fw-semibold small"><c:out value="${f.materia.nombre}"/></td>
                            <td class="small">${f.diaSemana}<br><span class="text-muted">${f.horaInicioStr}–${f.horaFinStr}</span></td>
                            <td><span class="badge" style="background:var(--uts-azul)">C-${f.cubiculo}</span></td>
                            <td>
                              <span class="badge ${f.inscritos >= f.maxEstudiantes ? 'text-bg-danger' : 'text-bg-success'}">
                                ${f.inscritos}/${f.maxEstudiantes}
                              </span>
                            </td>
                            <td>
                              <c:if test="${f.inscritos == 0}">
                                <form method="post" action="${pageContext.request.contextPath}/docente/semestre"
                                      onsubmit="return confirm('¿Eliminar esta franja?')">
                                  <input type="hidden" name="accion" value="eliminarFranja">
                                  <input type="hidden" name="franjaId" value="${f.id}">
                                  <button class="btn btn-danger btn-sm">
                                    <i class="bi bi-trash"></i>
                                  </button>
                                </form>
                              </c:if>
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
  </div>
</div>

<script>
// Mis propias franjas para marcarlas en verde en el mapa
const propias = [
  <c:forEach var="f" items="${misFranjas}" varStatus="vs">
    {dia:'${f.diaSemana}',hora:'${f.horaInicioStr}',cub:${f.cubiculo}}<c:if test="${!vs.last}">,</c:if>
  </c:forEach>
];
let selCub = null;

function construirMapa(ocupados) {
  const grid = document.getElementById('cubGrid');
  const dia  = document.getElementById('diaSelect').value;
  const hora = document.getElementById('horaSelect').value;
  grid.innerHTML = '';
  document.getElementById('cubLeyenda').classList.remove('d-none');
  for (let i = 1; i <= 60; i++) {
    const btn = document.createElement('button');
    btn.type = 'button'; btn.textContent = i; btn.className = 'cub-btn';
    const esPropio = propias.some(p => p.dia === dia && p.hora === hora && p.cub === i);
    if (esPropio) {
      btn.classList.add('propio'); btn.title = 'Tu cubículo en este horario';
    } else if (ocupados.includes(i)) {
      btn.classList.add('ocupado'); btn.title = 'Ocupado por otro tutor';
    } else {
      btn.addEventListener('click', function() { seleccionar(i, this); });
    }
    grid.appendChild(btn);
  }
  if (selCub && !ocupados.includes(selCub)) {
    grid.querySelectorAll('.cub-btn').forEach(b => {
      if (parseInt(b.textContent) === selCub) b.classList.add('selected');
    });
  } else { selCub = null; document.getElementById('cubInput').value = ''; }
}

function seleccionar(num, btn) {
  document.querySelectorAll('.cub-btn.selected').forEach(b => b.classList.remove('selected'));
  btn.classList.add('selected');
  selCub = num;
  document.getElementById('cubInput').value = num;
}

function cargarCubiculos() {
  const dia  = document.getElementById('diaSelect').value;
  const hora = document.getElementById('horaSelect').value;
  if (!dia || !hora) {
    document.getElementById('cubGrid').innerHTML = '';
    document.getElementById('cubStatus').textContent = '(selecciona día y hora primero)';
    document.getElementById('cubLeyenda').classList.add('d-none');
    return;
  }
  document.getElementById('cubStatus').textContent = 'Cargando…';
  const ctx = '${pageContext.request.contextPath}';
  fetch(ctx + '/docente/semestre?ajax=cubiculos&dia=' + encodeURIComponent(dia) + '&hora=' + encodeURIComponent(hora))
    .then(r => r.json())
    .then(ocupados => {
      construirMapa(ocupados);
      document.getElementById('cubStatus').textContent = '(' + (60 - ocupados.length) + ' cubículos libres de 60)';
    })
    .catch(() => { construirMapa([]); document.getElementById('cubStatus').textContent = ''; });
}

function chkCub() {
  if (!document.getElementById('cubInput').value) {
    alert('Debes seleccionar un cubículo del mapa.');
    return false;
  }
  return true;
}
</script>
</body></html>
