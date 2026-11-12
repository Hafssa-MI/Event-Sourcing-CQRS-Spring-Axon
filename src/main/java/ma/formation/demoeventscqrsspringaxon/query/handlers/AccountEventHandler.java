package ma.formation.demoeventscqrsspringaxon.query.handlers;

import lombok.extern.slf4j.Slf4j;
import ma.formation.demoeventscqrsspringaxon.enums.OperationType;
import ma.formation.demoeventscqrsspringaxon.events.*;
import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.entities.AccountOperation;
import ma.formation.demoeventscqrsspringaxon.query.queries.WatchEventQuery;
import ma.formation.demoeventscqrsspringaxon.query.repository.AccountRepository;
import ma.formation.demoeventscqrsspringaxon.query.repository.OperationRepository;
import org.apache.commons.logging.Log;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
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

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("======== Query Side AccountActivated Event Recieved =======");
        Account account= accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("======== Query Side AccountStatusUpdated Event Recieved =======");
        Account account= accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event,EventMessage eventMessage){
        log.info("======== Query Side AccountDebited Event Recieved =======");
        Account account= accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation=AccountOperation.builder()
                        .amount(event.getAmount())
                        .date(eventMessage.getTimestamp())
                        .type(OperationType.DEBIT)
                        .currency((event.getCurrency()))
                        .account(account)
                        .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance()-accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(
                WatchEventQuery.class,
                query -> query.getAccountId().equals(event.getAccountId()),
                accountOperation);
    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("======== Query Side AccountCredited Event Recieved =======");
        Account account= accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation=AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency((event.getCurrency()))
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance()+accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(
                WatchEventQuery.class,
                query -> query.getAccountId().equals(event.getAccountId()),
                accountOperation);
    }
}
