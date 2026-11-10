package ma.formation.demoeventscqrsspringaxon.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.formation.demoeventscqrsspringaxon.commands.commands.AddAccountCommand;
import ma.formation.demoeventscqrsspringaxon.commands.commands.CreditAccountCommand;
import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;
import ma.formation.demoeventscqrsspringaxon.events.AccountActivatedEvent;
import ma.formation.demoeventscqrsspringaxon.events.AccountCreatedEvent;
import ma.formation.demoeventscqrsspringaxon.events.AccountCreditedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
  private String accountId;
  private double balance;
  private AccountStatus status;
    //Pour Axon
  public AccountAggregate(){}
  @CommandHandler
  public AccountAggregate(AddAccountCommand command){
      log.info("############## AddAccuntCommand Received : #########");
      if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Balance doit etre positive");
      AggregateLifecycle.apply(new AccountCreatedEvent(
              command.getId(),
              command.getInitialBalance(),
              AccountStatus.CREATED,
              command.getCurrency()
      ));
      AggregateLifecycle.apply(new AccountActivatedEvent(
              command.getId(),
              AccountStatus.ACTIVATED
      ));
  }
  @EventSourcingHandler
  public void on(AccountCreatedEvent event){
      log.info("############## AccountCreatedEvent occured : #########");
      this.accountId = event.getAccountId();
      this.balance=event.getInitialBalance();
      this.status=event.getStatus();
  }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("############## AccountActivatedEvent occured : #########");
        this.accountId = event.getAccountId();
        this.status=event.getStatus();
    }
  @CommandHandler
  public void handle(CreditAccountCommand command){
        log.info("############## CreditAccuntCommand Received : #########");
        if(!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("The account"+command.getId()+" Is not activated");
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount doit etre positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
  }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("############## AccountCreditedEvent occured : #########");
        this.accountId = event.getAccountId();
        this.balance=this.balance+event.getAmount();
    }

}
