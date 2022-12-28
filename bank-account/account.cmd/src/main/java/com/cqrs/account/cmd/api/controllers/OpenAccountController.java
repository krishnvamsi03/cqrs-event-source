package com.cqrs.account.cmd.api.controllers;

import com.cqrs.account.cmd.api.commands.OpeningAccountCommand;
import com.cqrs.account.cmd.api.dto.OpenAccountResponse;
import com.cqrs.account.common.dto.BaseResponse;
import com.cqrs.cqrs.core.commands.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private Logger logger =
            Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpeningAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.sendCommand(command);
            return new ResponseEntity<>(new OpenAccountResponse("Account " +
                    "opened successfully ", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad" +
                    " request for id - {0}", id));
            return new ResponseEntity<>(new OpenAccountResponse(e.toString(),
                    id), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("error while " +
                    "processing request to open a new bank account - {0}",
                    id), e);
            var safeErrorMessage = MessageFormat.format("Error while " +
                    "processing request for this id ", id);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
