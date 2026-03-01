package com.tutores.util;

/**
 * Utilidad para regenerar el hash de la cuenta admin si es necesario.
 *
 * CREDENCIALES ADMIN:
 *   email:    admin@admin
 *   password: admin
 *
 * CÓMO USAR si el login del admin falla:
 *   mvn exec:java -Dexec.mainClass="com.tutores.util.AdminPasswordReset"
 *   Luego ejecuta el SQL impreso en la consola de PostgreSQL.
 */
public class AdminPasswordReset {

    public static void main(String[] args) {
        String hash = PasswordUtil.hash("admin");
        System.out.println();
        System.out.println("=== Actualizar hash del admin en PostgreSQL ===");
        System.out.println();
        System.out.println("UPDATE usuarios SET password_hash = '" + hash + "'");
        System.out.println("WHERE email = 'admin@admin';");
        System.out.println();
        System.out.println("Hash generado: " + hash);
    }
}
