package ma.formation.demoeventscqrsspringaxon.commands.dto;


import ma.formation.demoeventscqrsspringaxon.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}
