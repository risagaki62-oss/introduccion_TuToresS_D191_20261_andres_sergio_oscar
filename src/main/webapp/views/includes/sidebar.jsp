<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="u"   value="${sessionScope.usuario}"/>
<c:set var="rol" value="${u.rol}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<nav class="sidebar">
  <div class="p-3 border-bottom border-secondary-subtle">
    <div class="sidebar-logo">TUT<span class="verde-txt">o</span>reS</div>
    <div class="sidebar-user mt-1">
      <strong><c:out value="${u.nombreCompleto}"/></strong>
      <c:choose>
        <c:when test="${rol=='ADMIN'}">Administrador</c:when>
        <c:when test="${rol=='TUTOR'}">Tutor</c:when>
        <c:otherwise>Estudiante</c:otherwise>
      </c:choose>
    </div>
  </div>
  <div class="flex-grow-1 py-2">
    <c:if test="${rol=='ADMIN'}">
      <div class="nav-sec">Administración</div>
      <a href="${ctx}/admin/dashboard"    class="nav-item-link ${pageContext.request.servletPath.contains('dashboard')?'active':''}"><i class="bi bi-grid-fill me-2"></i>Dashboard</a>
      <a href="${ctx}/admin/aprobaciones" class="nav-item-link ${pageContext.request.servletPath.contains('aprob')?'active':''}"><i class="bi bi-check-circle me-2"></i>Aprobaciones</a>
      <a href="${ctx}/admin/usuarios"     class="nav-item-link ${pageContext.request.servletPath.contains('usuario')?'active':''}"><i class="bi bi-people-fill me-2"></i>Usuarios</a>
      <a href="${ctx}/admin/materias"     class="nav-item-link ${pageContext.request.servletPath.contains('materia')?'active':''}"><i class="bi bi-book-fill me-2"></i>Materias</a>
    </c:if>
    <c:if test="${rol=='TUTOR'}">
      <div class="nav-sec">Tutor</div>
      <a href="${ctx}/docente/dashboard"  class="nav-item-link ${pageContext.request.servletPath.contains('dashboard')?'active':''}"><i class="bi bi-grid-fill me-2"></i>Mi Panel</a>
      <a href="${ctx}/docente/semestre"   class="nav-item-link ${pageContext.request.servletPath.contains('semestre')?'active':''}"><i class="bi bi-calendar-week me-2"></i>Mis Horarios</a>
      <a href="${ctx}/docente/historial"  class="nav-item-link ${pageContext.request.servletPath.contains('historial')?'active':''}"><i class="bi bi-clock-history me-2"></i>Historial</a>
    </c:if>
    <c:if test="${rol=='ESTUDIANTE'}">
      <div class="nav-sec">Estudiante</div>
      <a href="${ctx}/estudiante/dashboard"  class="nav-item-link ${pageContext.request.servletPath.contains('dashboard')?'active':''}"><i class="bi bi-grid-fill me-2"></i>Mi Panel</a>
      <a href="${ctx}/estudiante/buscar"     class="nav-item-link ${pageContext.request.servletPath.contains('buscar')||pageContext.request.servletPath.contains('reservar')?'active':''}"><i class="bi bi-search me-2"></i>Buscar Tutoría</a>
      <a href="${ctx}/estudiante/historial"  class="nav-item-link ${pageContext.request.servletPath.contains('historial')?'active':''}"><i class="bi bi-clock-history me-2"></i>Historial</a>
    </c:if>
  </div>
  <div class="sidebar-footer">
    <a href="${ctx}/logout"><i class="bi bi-box-arrow-left me-2"></i>Cerrar sesión</a>
  </div>
</nav>
