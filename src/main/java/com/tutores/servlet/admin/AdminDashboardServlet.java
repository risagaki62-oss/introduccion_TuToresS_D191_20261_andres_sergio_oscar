package com.tutores.servlet.admin;

import com.tutores.dao.MateriaDAO;
import com.tutores.dao.TutoriaDAO;
import com.tutores.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            UsuarioDAO uDao = new UsuarioDAO();
            TutoriaDAO tDao = new TutoriaDAO();
            req.setAttribute("conteoRoles",    uDao.countByRol());
            req.setAttribute("pendientes",      uDao.countPendientes());
            req.setAttribute("conteoTutorias", tDao.countByEstado());
            req.setAttribute("topMaterias",    new MateriaDAO().topMaterias());
            req.setAttribute("topDocentes",    tDao.topTutores());
            req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error cargando dashboard admin", e);
        }
    }
}
