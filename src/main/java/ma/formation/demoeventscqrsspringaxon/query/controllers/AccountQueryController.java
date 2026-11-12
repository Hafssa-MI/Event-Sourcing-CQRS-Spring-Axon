package ma.formation.demoeventscqrsspringaxon.query.controllers;

import ma.formation.demoeventscqrsspringaxon.query.dtos.AccountStatementReponseDTO;
import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.queries.GetAccountStatementQuery;
import ma.formation.demoeventscqrsspringaxon.query.queries.GetAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> response =queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class));
        return response;
    }
    @GetMapping("/accountsStatement/{accountId}")
    public CompletableFuture<AccountStatementReponseDTO>  getAccountStatement(@PathVariable String accountId){
        return queryGateway.query(new GetAccountStatementQuery(accountId), ResponseTypes.instanceOf(AccountStatementReponseDTO.class));
    }
}
