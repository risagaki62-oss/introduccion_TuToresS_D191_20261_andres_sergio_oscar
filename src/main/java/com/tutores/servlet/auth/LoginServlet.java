package com.tutores.servlet.auth;

import com.tutores.dao.DocenteDAO;
import com.tutores.dao.EstudianteDAO;
import com.tutores.dao.UsuarioDAO;
import com.tutores.model.Docente;
import com.tutores.model.Estudiante;
import com.tutores.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        if (email == null || pass == null || email.isBlank() || pass.isBlank()) {
            req.setAttribute("error", "Correo y contraseña son obligatorios.");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            return;
        }

        try {
            Usuario usuario = new UsuarioDAO().findByEmail(email.trim().toLowerCase());

            if (usuario == null || !pass.equals(usuario.getPassword())) {
                req.setAttribute("error", "Correo o contraseña incorrectos.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                return;
            }

            if ("PENDIENTE".equals(usuario.getEstado())) {
                req.setAttribute("error", "Tu cuenta está pendiente de aprobación por el administrador.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                return;
            }
            if (!"APROBADO".equals(usuario.getEstado())) {
                req.setAttribute("error", "Tu cuenta no está activa. Comunícate con la administración.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", usuario);

            if ("TUTOR".equals(usuario.getRol())) {
                Docente d = new DocenteDAO().findByUsuarioId(usuario.getId());
                if (d != null) d.setUsuario(usuario);
                session.setAttribute("docente", d);
            }
            if ("ESTUDIANTE".equals(usuario.getRol())) {
                Estudiante e = new EstudianteDAO().findByUsuarioId(usuario.getId());
                if (e != null) e.setUsuario(usuario);
                session.setAttribute("estudiante", e);
            }

            String ctx = req.getContextPath();
            switch (usuario.getRol()) {
                case "ADMIN"      -> resp.sendRedirect(ctx + "/admin/dashboard");
                case "TUTOR"      -> resp.sendRedirect(ctx + "/docente/dashboard");
                case "ESTUDIANTE" -> resp.sendRedirect(ctx + "/estudiante/dashboard");
                default           -> resp.sendRedirect(ctx + "/login");
            }
        } catch (Exception e) {
            throw new ServletException("Error al iniciar sesión", e);
        }
    }
}
