package mx.gob.sev.serv.seguridad;

import mx.gob.sev.serv.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UsuarioDetalles extends User {
    private final Integer idUsuario;

    public UsuarioDetalles(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(
            usuario.getCuenta(),
            usuario.getContrasena(),
            usuario.getActivo() == 1,
            true, // accountNonExpired
            true, // credentialsNonExpired
            true, // accountNonLocked
            authorities
        );
        this.idUsuario = usuario.getId();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
}