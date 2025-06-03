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
            return "✅ Conexión exitosa a PIRT. Resultado: " + result + "\n" +
                   "Usuario: " + entityManager.createNativeQuery("SELECT CURRENT_USER").getSingleResult() + "\n" +
                   "Esquema: " + entityManager.createNativeQuery("SELECT SCHEMA_NAME()").getSingleResult();
        } catch (Exception e) {
            return "❌ Error de conexión:\n" + e.getMessage() + "\n\n" +
                   "Detalle técnico:\n" + e.getClass().getName();
        }
    }
}