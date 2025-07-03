package mx.gob.sev.serv.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordResetTool {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        
        // 1. Especifica la nueva contraseña en texto plano
        String nuevaContrasena = "1234"; // Cambia esto según necesites
        
        // 2. Generar el hash BCrypt
        String hashGenerado = encoder.encode(nuevaContrasena);
        
        // 3. Mostrar comando SQL para actualizar
        System.out.println("-- Ejecuta este SQL en tu base de datos --");
        System.out.println("UPDATE Usuarios SET contrasena = '" + hashGenerado + 
                         "' WHERE cuenta = 'lmorteo';");
        System.out.println("\n-- Hash generado --");
        System.out.println(hashGenerado);
    }
}