package epicentr.services;

import epicentr.entities.User;

public interface UserDetailsService {
    User loadUserByUsername(String username);
}
