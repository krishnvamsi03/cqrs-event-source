package com.cqrs.account.query.api.controllers;

import com.cqrs.account.query.api.dto.AccountLookupResponse;
import com.cqrs.account.query.api.dto.EqualityType;
import com.cqrs.account.query.api.queries.FindAccountByBalance;
import com.cqrs.account.query.api.queries.FindAllAccountsQuery;
import com.cqrs.account.query.api.queries.FindByAccountHolderQuery;
import com.cqrs.account.query.api.queries.FindByIdQuery;
import com.cqrs.account.query.domain.AccountEntity;
import com.cqrs.cqrs.core.domain.BaseEntity;
import com.cqrs.cqrs.core.queries.BaseQuery;
import com.cqrs.cqrs.core.queries.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/accountLookup")
public class AccountLookupController {

    private final Logger logger =
            Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccount() {
        try {
            List<BaseEntity> accounts =
                    queryDispatcher.send(new FindAllAccountsQuery());
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var accountResponse =
                    AccountLookupResponse.builder().accounts(accounts).message("successfully retrieved ").build();
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Failed to get all accounts";
            logger.log(Level.SEVERE, safeMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BaseEntity> accounts =
                    queryDispatcher.send(new FindByIdQuery(id));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var accountResponse =
                    AccountLookupResponse.builder().accounts(accounts).message("successfully retrieved ").build();
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Failed to get all accounts";
            logger.log(Level.SEVERE, safeMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{holder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "holder") String holder) {
        try {
            List<BaseEntity> accounts =
                    queryDispatcher.send(new FindByAccountHolderQuery(holder));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var accountResponse =
                    AccountLookupResponse.builder().accounts(accounts).message("successfully retrieved ").build();
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Failed to get all accounts";
            logger.log(Level.SEVERE, safeMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byBalance/{eq}/{bal}")
    public ResponseEntity<AccountLookupResponse> getByBalance(@PathVariable(value = "eq")EqualityType type, @PathVariable(value = "bal") double bal) {
        try {
            List<BaseEntity> accounts =
                    queryDispatcher.send(new FindAccountByBalance(type, bal));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var accountResponse =
                    AccountLookupResponse.builder().accounts(accounts).message("successfully retrieved ").build();
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Failed to get all accounts";
            logger.log(Level.SEVERE, safeMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
