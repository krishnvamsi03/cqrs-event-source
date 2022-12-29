package com.cqrs.account.cmd.api.controllers;

import com.cqrs.account.cmd.api.commands.RestoreReadDBCommand;
import com.cqrs.account.common.dto.BaseResponse;
import com.cqrs.cqrs.core.commands.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class RestoreDbController {

    private Logger logger =
            Logger.getLogger(RestoreDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB() {
        try {
            commandDispatcher.sendCommand(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Read db " +
                    "restore successfully "), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "error while processing request to open " +
                            "a new bank account",
                    e);
            var safeErrorMessage = "Error while processing request for this " +
                    "id ";
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
