package com.tutores.servlet.docente;

import com.tutores.dao.FranjaHorariaDAO;
import com.tutores.dao.TutoriaDAO;
import com.tutores.model.Docente;
import com.tutores.util.SemestreUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

public class DocenteDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession s = req.getSession(false);
            Docente d = s != null ? (Docente) s.getAttribute("docente") : null;
            String sem = SemestreUtil.getSemestreActual();
            req.setAttribute("semestre", sem);
            req.setAttribute("tutorias", d != null ? new TutoriaDAO().findActivasByDocente(d.getId()) : Collections.emptyList());
            req.setAttribute("franjas",  d != null ? new FranjaHorariaDAO().findByDocente(d.getId(), sem) : Collections.emptyList());
            req.getRequestDispatcher("/views/docente/dashboard.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
