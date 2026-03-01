package com.tutores.servlet.auth;

import com.tutores.dao.DocenteDAO;
import com.tutores.dao.EstudianteDAO;
import com.tutores.dao.UsuarioDAO;
import com.tutores.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre   = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email    = req.getParameter("email");
        String pass     = req.getParameter("password");
        String rol      = req.getParameter("rol");
        String titulo   = req.getParameter("titulo");

        // Validaciones básicas
        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()
                || email == null || email.isBlank() || pass == null || pass.isBlank()
                || rol == null || rol.isBlank()) {
            req.setAttribute("error", "Todos los campos obligatorios deben completarse.");
            req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
            return;
        }

        String emailLow = email.trim().toLowerCase();

        // Validación de dominio UTS
        if ("ESTUDIANTE".equals(rol) && !emailLow.endsWith("@uts.edu.co")) {
            req.setAttribute("error", "Los estudiantes deben usar su correo @uts.edu.co");
            req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
            return;
        }
        if ("TUTOR".equals(rol) && !emailLow.endsWith("@correo.uts.edu.co")) {
            req.setAttribute("error", "Los tutores deben usar su correo @correo.uts.edu.co");
            req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
            return;
        }

        try {
            UsuarioDAO uDao = new UsuarioDAO();
            if (uDao.findByEmail(emailLow) != null) {
                req.setAttribute("error", "Ese correo ya está registrado.");
                req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
                return;
            }

            Usuario u = new Usuario();
            u.setNombre(nombre.trim()); u.setApellido(apellido.trim());
            u.setEmail(emailLow); u.setPassword(pass);
            u.setRol(rol); u.setEstado("PENDIENTE");

            int uid = uDao.insert(u);
            switch (rol) {
                case "TUTOR"      -> new DocenteDAO().insert(uid, titulo != null ? titulo.trim() : null);
                case "ESTUDIANTE" -> new EstudianteDAO().insert(uid);
            }

            req.setAttribute("exito", "Registro exitoso. Tu cuenta quedará pendiente hasta que el administrador la apruebe.");
            req.getRequestDispatcher("/views/auth/registro.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error al registrar usuario", e);
        }
    }
}
