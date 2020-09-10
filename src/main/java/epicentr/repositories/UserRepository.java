package epicentr.repositories;

import java.util.Optional;

import epicentr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Ramesh Fadatare
 *
 */
public interface UserRepository extends JpaRepository<User, Long>
{

	User findByEmail(String email);
	User findByName(String name);

}
