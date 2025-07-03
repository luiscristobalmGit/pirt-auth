package mx.gob.sev.serv.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        // 1. Configuración idéntica a tu aplicación
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        
        // 2. Datos de prueba (usa TU hash real)
        String encodedPasswordFromDB = "$2a$12$ooUlk.UwSOmFZxkxbS1SoelRRuROATk..y9A/e6y4qlsSwxDMq5/2";
        String rawPasswordToTest = "1234"; // La contraseña que quieres verificar
        
        // 3. Verificación detallada
        System.out.println("=== Verificador BCrypt ===");
        System.out.println("Hash almacenado: " + encodedPasswordFromDB);
        System.out.println("Contraseña a verificar: " + rawPasswordToTest);
        
        boolean isMatch = encoder.matches(rawPasswordToTest, encodedPasswordFromDB);
        
        System.out.println("\nResultado: " + (isMatch ? "✅ COINCIDEN" : "❌ NO COINCIDEN"));
        
        // 4. Diagnóstico adicional
        if (!isMatch) {
            System.out.println("\n🔍 Posibles causas:");
            System.out.println("- Espacios en blanco en la contraseña: '" + rawPasswordToTest + "'");
            System.out.println("- El hash no fue generado con fuerza 12");
            System.out.println("- La contraseña original no era '1234'");
            
            // Generar un nuevo hash para comparación
            String newHash = encoder.encode(rawPasswordToTest);
            System.out.println("\nHash generado ahora con la misma contraseña: " + newHash);
        }
    }
}