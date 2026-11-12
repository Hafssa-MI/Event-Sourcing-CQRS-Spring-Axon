package ma.formation.demoeventscqrsspringaxon.query.controllers;

import lombok.extern.slf4j.Slf4j;
import ma.formation.demoeventscqrsspringaxon.query.dtos.AccountStatementReponseDTO;
import ma.formation.demoeventscqrsspringaxon.query.entities.Account;
import ma.formation.demoeventscqrsspringaxon.query.entities.AccountOperation;
import ma.formation.demoeventscqrsspringaxon.query.queries.GetAccountStatementQuery;
import ma.formation.demoeventscqrsspringaxon.query.queries.GetAllAccountsQuery;
import ma.formation.demoeventscqrsspringaxon.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Slf4j
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
    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId) {
        SubscriptionQueryResult<AccountOperation, AccountOperation> result =
                queryGateway.subscriptionQuery(
                        new WatchEventQuery(accountId),
                        ResponseTypes.instanceOf(AccountOperation.class),
                        ResponseTypes.instanceOf(AccountOperation.class));

        return result.initialResult()
                .filter(op -> op.getId() != null)
                .doOnNext(op -> log.info("INITIAL: {}", op))
                .concatWith(result.updates()
                        .doOnNext(op -> log.info("UPDATE reçu: {}", op.getAmount()))
                        .doOnError(e -> log.error("Erreur updates", e)))
                .doFinally(signal -> {
                    log.info("Subscription fermée: {}", signal);
                    result.close();
                });
    }
}
