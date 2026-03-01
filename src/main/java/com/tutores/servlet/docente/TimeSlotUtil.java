package com.tutores.servlet.docente;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeSlotUtil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm");
    private static final List<String> SLOTS;
    static {
        List<String> s = new ArrayList<>();
        LocalTime t = LocalTime.of(7, 30);
        LocalTime fin = LocalTime.of(18, 17);
        while (t.isBefore(fin)) { s.add(t.format(FMT)); t = t.plusMinutes(44); }
        SLOTS = Collections.unmodifiableList(s);
    }
    public static List<String> getSlots() { return SLOTS; }
}
