package com.tutores.util;

import java.time.LocalDate;

/** Calcula el semestre académico actual: YYYY-1 (ene–jun) o YYYY-2 (jul–dic). */
public class SemestreUtil {
    public static String getSemestreActual() {
        LocalDate hoy = LocalDate.now();
        return hoy.getYear() + "-" + (hoy.getMonthValue() <= 6 ? "1" : "2");
    }
}
