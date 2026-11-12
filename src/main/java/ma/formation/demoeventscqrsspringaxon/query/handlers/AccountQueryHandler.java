package ma.formation.demoeventscqrsspringaxon.query.handlers;

import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.queries.GetAllAccountsQuery;
import ma.formation.demoeventscqrsspringaxon.query.repository.AccountRepository;
import ma.formation.demoeventscqrsspringaxon.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
}
