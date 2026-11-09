package ma.formation.demoeventscqrsspringaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;
@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;

}
