package mx.gob.sev.serv.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordVerifier {
    public static void main(String[] args) {
        // 1. Configurar el encoder con la misma fuerza que tu aplicación
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        
        // 2. Datos REALES de tu base de datos
        String hashDeBD = "$2a$12$ooUlk.UwSOmFZxkxbS1SoelRRuROATk..y9A/e6y4qlsSwxDMq5/2";
        String passwordPrueba = "1234";
        
        // 3. Eliminar espacios (solución común)
        passwordPrueba = passwordPrueba.trim();
        
        // 4. Verificación detallada
        System.out.println("=== VERIFICACIÓN BCrypt ===");
        System.out.println("Hash de BD: " + hashDeBD);
        System.out.println("Password a probar: '" + passwordPrueba + "'");
        
        boolean coincide = encoder.matches(passwordPrueba, hashDeBD);
        
        if (coincide) {
            System.out.println("✅ La contraseña ES VÁLIDA");
        } else {
            System.out.println("❌ La contraseña NO COINCIDE");
            System.out.println("\n🔍 Generando nuevo hash para comparación:");
            String nuevoHash = encoder.encode(passwordPrueba);
            System.out.println("Nuevo hash con la misma contraseña: " + nuevoHash);
        }
    }
}