package com.schoolarch.api.controllers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.CreateBatchCommand;
import com.schoolarch.application.commands.Commands.OpenRegistrationCommand;
import com.schoolarch.application.handlers.CreateBatchCommandHandler;
import com.schoolarch.application.handlers.OpenRegistrationCommandHandler;
import com.schoolarch.domain.aggregates.AcademicBatch;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batches")
public class AcademicBatchController {

    private final CreateBatchCommandHandler createBatchHandler;
    private final OpenRegistrationCommandHandler openRegistrationHandler;

    public AcademicBatchController(
            CreateBatchCommandHandler createBatchHandler,
            OpenRegistrationCommandHandler openRegistrationHandler) {
        this.createBatchHandler = createBatchHandler;
        this.openRegistrationHandler = openRegistrationHandler;
    }

    @PostMapping("/create")
    public CommandResult<AcademicBatch> createBatch(@RequestBody CreateBatchCommand cmd) {
        return createBatchHandler.handle(cmd);
    }

    @PostMapping("/open-registration")
    public CommandResult<AcademicBatch> openRegistration(@RequestBody OpenRegistrationCommand cmd) {
        return openRegistrationHandler.handle(cmd);
    }
}
