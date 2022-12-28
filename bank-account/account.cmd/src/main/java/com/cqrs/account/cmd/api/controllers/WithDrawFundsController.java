package com.cqrs.account.cmd.api.controllers;

import com.cqrs.account.cmd.api.commands.DepositFundsCommand;
import com.cqrs.account.cmd.api.commands.WithDrawFundsCommand;
import com.cqrs.account.common.dto.BaseResponse;
import com.cqrs.cqrs.core.commands.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withDrawFunds")
public class WithDrawFundsController {

    private Logger logger =
            Logger.getLogger(WithDrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value =
            "id") String id, @RequestBody WithDrawFundsCommand command) {
        command.setId(id);
        try {
            commandDispatcher.sendCommand(command);
            return new ResponseEntity<>(new BaseResponse("Account " +
                    "opened successfully "), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad" +
                    " request for id - {0}", id));
            return new ResponseEntity<>(new BaseResponse(e.toString()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("error while " +
                            "processing request to open a new bank account - " +
                            "{0}",
                    id), e);
            var safeErrorMessage = MessageFormat.format("Error while " +
                    "processing request for this id ", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
