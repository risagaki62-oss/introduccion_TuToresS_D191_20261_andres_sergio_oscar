package com.tutores.servlet.estudiante;
import com.tutores.dao.FranjaHorariaDAO; import com.tutores.dao.TutoriaDAO;
import com.tutores.model.Estudiante; import com.tutores.model.FranjaHoraria;
import javax.servlet.ServletException; import javax.servlet.http.*;
import java.io.IOException;
public class ReservarServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getParameter("franjaId");
        if (ip==null||ip.isBlank()) { resp.sendRedirect(req.getContextPath()+"/estudiante/buscar"); return; }
        try {
            FranjaHoraria f = new FranjaHorariaDAO().findById(Integer.parseInt(ip));
            if (f==null) { resp.sendRedirect(req.getContextPath()+"/estudiante/buscar?error=nofound"); return; }
            req.setAttribute("franja", f);
            req.getRequestDispatcher("/views/estudiante/reservar.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String ip = req.getParameter("franjaId"), desc = req.getParameter("descripcion");
        if (ip==null||desc==null||desc.isBlank()) { resp.sendRedirect(req.getContextPath()+"/estudiante/buscar"); return; }
        try {
            HttpSession s = req.getSession(false);
            Estudiante est = s != null ? (Estudiante) s.getAttribute("estudiante") : null;
            if (est==null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
            int franjaId = Integer.parseInt(ip);
            FranjaHoraria f = new FranjaHorariaDAO().findById(franjaId);
            TutoriaDAO tDao = new TutoriaDAO();
            if (f==null||!f.isDisponible()) { resp.sendRedirect(req.getContextPath()+"/estudiante/buscar?error=llena"); return; }
            if (tDao.yaInscrito(franjaId, est.getId())) { resp.sendRedirect(req.getContextPath()+"/estudiante/buscar?error=inscrito"); return; }
            tDao.insert(franjaId, est.getId(), desc.trim());
            resp.sendRedirect(req.getContextPath() + "/estudiante/dashboard?msg=reservada");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
