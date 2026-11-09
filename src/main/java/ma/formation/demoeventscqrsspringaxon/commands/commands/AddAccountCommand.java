package ma.formation.demoeventscqrsspringaxon.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.AggregateIdentifier;

@Getter @AllArgsConstructor
public class AddAccountCommand {
    @AggregateIdentifier
    private String id;
    private double initialBalance;
    private String currency;
}
