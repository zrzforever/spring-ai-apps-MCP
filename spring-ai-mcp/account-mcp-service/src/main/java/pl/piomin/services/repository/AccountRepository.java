package pl.piomin.services.repository;

import org.springframework.data.repository.CrudRepository;
import pl.piomin.services.model.Account;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByPersonId(Long personId);
}
