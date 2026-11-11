package ma.formation.demoeventscqrsspringaxon.query.repository;

import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
