package mx.gob.sev.serv.service;

import mx.gob.sev.serv.model.Usuario;
import mx.gob.sev.serv.model.Rol;
import mx.gob.sev.serv.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cuenta) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuenta(cuenta)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + cuenta));
        
        if (usuario.getActivo() != 1) {
            throw new UsernameNotFoundException("Usuario inactivo: " + cuenta);
        }

        return new User(
            usuario.getCuenta(),
            usuario.getContrasena(),
            usuario.getRoles().stream()
                .filter(rol -> rol.getActivo() == 1)
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getTipoRol().getNombre()))
                .collect(Collectors.toList())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Rol> roles) {
        return roles.stream()
            .filter(rol -> rol.getActivo() == 1)
            .map(rol -> {
                String nombreRol = rol.getTipoRol().getNombre().trim().toUpperCase();
                if (!nombreRol.startsWith("ROLE_")) {
                    nombreRol = "ROLE_" + nombreRol;
                }
                return new SimpleGrantedAuthority(nombreRol);
            })
            .collect(Collectors.toList());
    }
}