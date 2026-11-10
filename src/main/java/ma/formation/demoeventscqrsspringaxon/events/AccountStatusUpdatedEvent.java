package ma.formation.demoeventscqrsspringaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;

}
