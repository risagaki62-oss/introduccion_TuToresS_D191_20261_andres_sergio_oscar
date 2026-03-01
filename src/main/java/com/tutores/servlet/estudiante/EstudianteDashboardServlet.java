package com.tutores.servlet.estudiante;
import com.tutores.dao.TutoriaDAO; import com.tutores.model.Estudiante;
import javax.servlet.ServletException; import javax.servlet.http.*;
import java.io.IOException; import java.util.Collections;
public class EstudianteDashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession s = req.getSession(false);
            Estudiante e = s != null ? (Estudiante) s.getAttribute("estudiante") : null;
            req.setAttribute("tutorias", e != null ? new TutoriaDAO().findActivasByEstudiante(e.getId()) : Collections.emptyList());
            req.getRequestDispatcher("/views/estudiante/dashboard.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
