package com.tutores.servlet.admin;

import com.tutores.dao.DocenteDAO;
import com.tutores.dao.EstudianteDAO;
import com.tutores.dao.UsuarioDAO;
import com.tutores.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GestionUsuariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("usuarios", new UsuarioDAO().findAllExceptAdmin());
            req.getRequestDispatcher("/views/admin/usuarios.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        UsuarioDAO uDao = new UsuarioDAO();
        try {
            switch (accion != null ? accion : "") {
                case "crear" -> {
                    String nombre   = req.getParameter("nombre");
                    String apellido = req.getParameter("apellido");
                    String email    = req.getParameter("email");
                    String pass     = req.getParameter("password");
                    String rol      = req.getParameter("rol");
                    String titulo   = req.getParameter("titulo");
                    if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
                        req.setAttribute("errorCrear", "Nombre y correo son obligatorios.");
                        req.setAttribute("usuarios", uDao.findAllExceptAdmin());
                        req.getRequestDispatcher("/views/admin/usuarios.jsp").forward(req, resp);
                        return;
                    }
                    String emailLow = email.trim().toLowerCase();
                    if (uDao.findByEmail(emailLow) != null) {
                        req.setAttribute("errorCrear", "El correo ya está registrado.");
                        req.setAttribute("usuarios", uDao.findAllExceptAdmin());
                        req.getRequestDispatcher("/views/admin/usuarios.jsp").forward(req, resp);
                        return;
                    }
                    Usuario u = new Usuario();
                    u.setNombre(nombre.trim()); u.setApellido(apellido != null ? apellido.trim() : "");
                    u.setEmail(emailLow); u.setPassword(pass != null ? pass : "123456");
                    u.setRol(rol); u.setEstado("APROBADO");
                    int uid = uDao.insert(u);
                    switch (rol != null ? rol : "") {
                        case "TUTOR"      -> new DocenteDAO().insert(uid, titulo != null ? titulo.trim() : null);
                        case "ESTUDIANTE" -> new EstudianteDAO().insert(uid);
                    }
                    resp.sendRedirect(req.getContextPath() + "/admin/usuarios?msg=creado");
                }
                case "activar"   -> { uDao.updateEstado(Integer.parseInt(req.getParameter("userId")), "APROBADO");
                                      resp.sendRedirect(req.getContextPath() + "/admin/usuarios?msg=ok"); }
                case "desactivar"-> { uDao.updateEstado(Integer.parseInt(req.getParameter("userId")), "INACTIVO");
                                      resp.sendRedirect(req.getContextPath() + "/admin/usuarios?msg=ok"); }
                case "eliminar"  -> { uDao.delete(Integer.parseInt(req.getParameter("userId")));
                                      resp.sendRedirect(req.getContextPath() + "/admin/usuarios?msg=eliminado"); }
                default          -> resp.sendRedirect(req.getContextPath() + "/admin/usuarios");
            }
        } catch (Exception e) { throw new ServletException(e); }
    }
}
