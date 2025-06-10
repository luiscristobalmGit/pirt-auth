package mx.gob.sev.serv.service;

import mx.gob.sev.serv.model.Usuario;
import mx.gob.sev.serv.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuenta(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        return User.builder()
            .username(usuario.getCuenta())
            .password(usuario.getContrasena())
            .disabled(usuario.getActivo() != 1)
            .roles("USER") // Rol básico, se ajustará según la lógica real
            .build();
    }
}