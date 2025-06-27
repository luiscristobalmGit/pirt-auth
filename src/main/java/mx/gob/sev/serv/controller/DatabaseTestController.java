package mx.gob.sev.serv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
public class DatabaseTestController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/test-db")
    public String testConnection() {
        try {
            Object result = entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return "✅ Conexión exitosa. Resultado: " + result + "\n" +
                   "Usuario: " + entityManager.createNativeQuery("SELECT CURRENT_USER").getSingleResult();
        } catch (Exception e) {
            return "❌ Error de conexión: " + e.getMessage();
        }
    }
}