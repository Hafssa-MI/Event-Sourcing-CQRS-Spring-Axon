package ma.formation.demoeventscqrsspringaxon.query.handlers;

import lombok.extern.slf4j.Slf4j;
import ma.formation.demoeventscqrsspringaxon.events.AccountCreatedEvent;
import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.repository.AccountRepository;
import ma.formation.demoeventscqrsspringaxon.query.repository.OperationRepository;
import org.apache.commons.logging.Log;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("======== Query Side AccountCreated Event Recieved =======");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }
}
