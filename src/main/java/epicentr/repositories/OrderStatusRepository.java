package epicentr.repositories;

import epicentr.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
