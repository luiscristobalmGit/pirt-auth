package mx.gob.sev.serv.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BCryptPasswordEncoder.class})
@ActiveProfiles("test")
public class PasswordTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testPasswordMatch() {
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword),
              "La contraseña debería coincidir con el hash generado");
    }

    @Test
    public void testPasswordMismatch() {
        String rawPassword = "1234";
        String wrongPassword = "password_incorrecto";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertFalse(passwordEncoder.matches(wrongPassword, encodedPassword),
              "No debería coincidir con contraseña incorrecta");
    }
}