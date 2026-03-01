package com.tutores.servlet.docente;

import com.tutores.dao.TutoriaDAO;
import com.tutores.model.Docente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

public class HistorialDocenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession s = req.getSession(false);
            Docente d = s != null ? (Docente) s.getAttribute("docente") : null;
            req.setAttribute("historial", d != null ? new TutoriaDAO().findHistorialByDocente(d.getId()) : Collections.emptyList());
            req.getRequestDispatcher("/views/docente/historial.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
