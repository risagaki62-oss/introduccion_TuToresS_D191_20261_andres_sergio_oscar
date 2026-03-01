package com.tutores.servlet.admin;

import com.tutores.dao.MateriaDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GestionMateriasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("materias", new MateriaDAO().findAll());
            req.getRequestDispatcher("/views/admin/materias.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        try {
            MateriaDAO dao = new MateriaDAO();
            switch (accion != null ? accion : "") {
                case "crear" -> {
                    String nombre = req.getParameter("nombre");
                    String desc   = req.getParameter("descripcion");
                    if (nombre != null && !nombre.isBlank())
                        dao.insert(nombre.trim(), desc != null ? desc.trim() : null);
                }
                case "toggle" -> dao.toggleActivo(Integer.parseInt(req.getParameter("materiaId")));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/materias?msg=ok");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
