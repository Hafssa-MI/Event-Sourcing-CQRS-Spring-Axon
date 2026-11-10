package ma.formation.demoeventscqrsspringaxon.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}


