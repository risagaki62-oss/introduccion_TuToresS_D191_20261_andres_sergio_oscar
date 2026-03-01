package com.tutores.servlet.estudiante;
import com.tutores.dao.FranjaHorariaDAO; import com.tutores.dao.MateriaDAO;
import com.tutores.model.FranjaHoraria; import com.tutores.util.SemestreUtil;
import javax.servlet.ServletException; import javax.servlet.http.*;
import java.io.IOException; import java.util.*;
public class BuscarTutoriaServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("materias", new MateriaDAO().findActivas());
            String mp = req.getParameter("materiaId");
            if (mp != null && !mp.isBlank()) {
                int mid = Integer.parseInt(mp);
                req.setAttribute("materiaSeleccionada", mid);
                List<FranjaHoraria> franjas = new FranjaHorariaDAO().findDisponiblesByMateria(mid, SemestreUtil.getSemestreActual());
                Map<Integer, Map<String,Object>> map = new LinkedHashMap<>();
                for (FranjaHoraria f : franjas) {
                    map.computeIfAbsent(f.getDocenteId(), k -> { Map<String,Object> m = new LinkedHashMap<>(); m.put("docente",f.getDocente()); m.put("franjas",new ArrayList<FranjaHoraria>()); return m; });
                    @SuppressWarnings("unchecked") List<FranjaHoraria> lst = (List<FranjaHoraria>) map.get(f.getDocenteId()).get("franjas");
                    lst.add(f);
                }
                req.setAttribute("resultados", new ArrayList<>(map.values()));
            }
            req.getRequestDispatcher("/views/estudiante/buscarTutoria.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
