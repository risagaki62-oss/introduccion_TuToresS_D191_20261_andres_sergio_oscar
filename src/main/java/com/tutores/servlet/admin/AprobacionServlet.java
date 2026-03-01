package com.tutores.servlet.admin;

import com.tutores.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AprobacionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("pendientes", new UsuarioDAO().findByEstado("PENDIENTE"));
            req.getRequestDispatcher("/views/admin/aprobaciones.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        String idParam = req.getParameter("userId");
        if (accion == null || idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/aprobaciones");
            return;
        }
        try {
            int uid = Integer.parseInt(idParam);
            String estado = "aprobar".equals(accion) ? "APROBADO" : "RECHAZADO";
            new UsuarioDAO().updateEstado(uid, estado);
            resp.sendRedirect(req.getContextPath() + "/admin/aprobaciones?msg=ok");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
