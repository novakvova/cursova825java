package epicentr.services;

import epicentr.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        epicentr.entities.User applicationUser = applicationUserRepository.findByEmail(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getEmail(),applicationUser.getPassword(),new ArrayList<>());
    }
}
