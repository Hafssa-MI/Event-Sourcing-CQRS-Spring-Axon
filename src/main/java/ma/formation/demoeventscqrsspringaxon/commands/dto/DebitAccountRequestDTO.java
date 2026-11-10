package ma.formation.demoeventscqrsspringaxon.commands.dto;


public record DebitAccountRequestDTO(String accountId, double amount , String currency) {
}
