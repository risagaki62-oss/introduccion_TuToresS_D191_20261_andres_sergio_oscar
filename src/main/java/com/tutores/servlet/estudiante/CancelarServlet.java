package com.tutores.servlet.estudiante;
import com.tutores.dao.TutoriaDAO; import com.tutores.model.Estudiante; import com.tutores.model.Tutoria;
import javax.servlet.ServletException; import javax.servlet.http.*;
import java.io.IOException;
public class CancelarServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String tip = req.getParameter("tutoriaId");
        if (tip==null) { resp.sendRedirect(req.getContextPath()+"/estudiante/dashboard"); return; }
        try {
            HttpSession s = req.getSession(false);
            Estudiante est = s != null ? (Estudiante) s.getAttribute("estudiante") : null;
            TutoriaDAO tDao = new TutoriaDAO();
            Tutoria t = tDao.findById(Integer.parseInt(tip));
            if (t!=null && "ACTIVA".equals(t.getEstado()) && (est==null || t.getEstudianteId()==est.getId()))
                tDao.updateEstado(t.getId(), "CANCELADA");
            resp.sendRedirect(req.getContextPath() + "/estudiante/dashboard?msg=cancelada");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
