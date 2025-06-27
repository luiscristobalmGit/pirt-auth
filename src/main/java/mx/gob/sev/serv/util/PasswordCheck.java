package mx.gob.sev.serv.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        
        // Usa el NUEVO hash generado
        String encodedPassword = "$2a$12$ooUlk.UwSOmFZxkxbS1SoelRRuROATk..y9A/e6y4qlsSwxDMq5/2";
        String rawPassword = "1234";
        
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Verificación con nuevo hash: " + matches);
    }
}