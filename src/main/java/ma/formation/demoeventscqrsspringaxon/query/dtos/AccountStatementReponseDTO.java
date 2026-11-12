package ma.formation.demoeventscqrsspringaxon.query.dtos;

import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementReponseDTO(
        Account account, List<AccountOperation> operations
        ) {
}
