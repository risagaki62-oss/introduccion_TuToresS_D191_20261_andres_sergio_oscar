package com.tutores.servlet.docente;

import com.tutores.dao.FranjaHorariaDAO;
import com.tutores.dao.MateriaDAO;
import com.tutores.model.Docente;
import com.tutores.util.SemestreUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ConfigSemestreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("cubiculos".equals(req.getParameter("ajax"))) {
            String dia = req.getParameter("dia"), hora = req.getParameter("hora");
            resp.setContentType("application/json;charset=UTF-8");
            if (dia == null || hora == null || dia.isBlank() || hora.isBlank()) {
                resp.getWriter().write("[]"); return;
            }
            try {
                List<Integer> oc = new FranjaHorariaDAO().getCubiculosOcupados(dia, hora, SemestreUtil.getSemestreActual());
                resp.getWriter().write(toJson(oc));
            } catch (Exception e) { resp.getWriter().write("[]"); }
            return;
        }
        loadVista(req, resp, getDocente(req), null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Docente d = getDocente(req);
        String sem = SemestreUtil.getSemestreActual();
        String accion = req.getParameter("accion");

        try {
            FranjaHorariaDAO fDao = new FranjaHorariaDAO();
            if ("agregarFranja".equals(accion)) {
                String mId = req.getParameter("materiaId"), dia = req.getParameter("dia"),
                       hora = req.getParameter("hora"), cub = req.getParameter("cubiculo"),
                       max = req.getParameter("maxEstudiantes");
                if (mId==null||dia==null||hora==null||cub==null||max==null) {
                    loadVista(req, resp, d, "Todos los campos son obligatorios."); return;
                }
                int cubiculo = Integer.parseInt(cub), maxEst = Integer.parseInt(max);
                if (cubiculo<1||cubiculo>60) { loadVista(req, resp, d, "Cubículo entre 1 y 60."); return; }
                if (maxEst<1||maxEst>10)     { loadVista(req, resp, d, "Máximo de estudiantes entre 1 y 10."); return; }
                if (fDao.cubiculoOcupado(dia, hora, sem, cubiculo, -1)) {
                    loadVista(req, resp, d, "Cubículo "+cubiculo+" ya reservado en ese horario."); return;
                }
                fDao.insert(d != null ? d.getId() : 0, Integer.parseInt(mId), dia, hora, sem, cubiculo, maxEst);
                resp.sendRedirect(req.getContextPath() + "/docente/semestre?msg=ok");
            } else if ("eliminarFranja".equals(accion)) {
                String idP = req.getParameter("franjaId");
                if (idP==null) { resp.sendRedirect(req.getContextPath()+"/docente/semestre"); return; }
                int franjaId = Integer.parseInt(idP);
                if (fDao.tieneReservasActivas(franjaId)) {
                    loadVista(req, resp, d, "No puedes eliminar: hay estudiantes inscritos."); return;
                }
                fDao.delete(franjaId);
                resp.sendRedirect(req.getContextPath() + "/docente/semestre?msg=eliminada");
            } else {
                resp.sendRedirect(req.getContextPath() + "/docente/semestre");
            }
        } catch (NumberFormatException e) {
            loadVista(req, resp, d, "Valor numérico inválido.");
        } catch (Exception e) { throw new ServletException(e); }
    }

    private Docente getDocente(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null ? (Docente) s.getAttribute("docente") : null;
    }

    private void loadVista(HttpServletRequest req, HttpServletResponse resp, Docente d, String error)
            throws ServletException, IOException {
        try {
            String sem = SemestreUtil.getSemestreActual();
            if (error != null) req.setAttribute("errorFranja", error);
            req.setAttribute("semestre",   sem);
            req.setAttribute("materias",   new MateriaDAO().findActivas());
            req.setAttribute("misFranjas", d != null ? new FranjaHorariaDAO().findByDocente(d.getId(), sem) : Collections.emptyList());
            req.setAttribute("timeSlots",  TimeSlotUtil.getSlots());
            req.getRequestDispatcher("/views/docente/configSemestre.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    private String toJson(List<Integer> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0;i<list.size();i++) { sb.append(list.get(i)); if(i<list.size()-1) sb.append(","); }
        return sb.append("]").toString();
    }
}
