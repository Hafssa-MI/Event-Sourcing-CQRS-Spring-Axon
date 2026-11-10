package ma.formation.demoeventscqrsspringaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;

}
