package epicentr.services;

import epicentr.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
