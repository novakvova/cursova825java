package epicentr.services;

import epicentr.entities.User;
import epicentr.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User applicationUser = applicationUserRepository.findByName(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        User usik = new User();
        usik.setName(applicationUser.getName());
        usik.setPassword(applicationUser.getPassword());
        return usik;
    }
}
