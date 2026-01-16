package com.schoolarch.api.controllers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.AddSectionCommand;
import com.schoolarch.application.commands.Commands.AssignStaffCommand;
import com.schoolarch.application.commands.Commands.ConfigureOfferingCommand;
import com.schoolarch.application.handlers.AddSectionCommandHandler;
import com.schoolarch.application.handlers.AssignStaffCommandHandler;
import com.schoolarch.application.handlers.ConfigureOfferingCommandHandler;
import com.schoolarch.domain.aggregates.InstructionalOffering;
import com.schoolarch.domain.aggregates.Section;
import com.schoolarch.domain.repositories.Repositories.StaffAssignment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offerings")
public class InstructionalOfferingController {

    private final ConfigureOfferingCommandHandler configureHandler;
    private final AddSectionCommandHandler addSectionHandler;
    private final AssignStaffCommandHandler assignStaffHandler;

    public InstructionalOfferingController(
            ConfigureOfferingCommandHandler configureHandler,
            AddSectionCommandHandler addSectionHandler,
            AssignStaffCommandHandler assignStaffHandler) {
        this.configureHandler = configureHandler;
        this.addSectionHandler = addSectionHandler;
        this.assignStaffHandler = assignStaffHandler;
    }

    @PostMapping("/configure")
    public CommandResult<InstructionalOffering> configure(@RequestBody ConfigureOfferingCommand cmd) {
        return configureHandler.handle(cmd);
    }

    @PostMapping("/sections/add")
    public CommandResult<Section> addSection(@RequestBody AddSectionCommand cmd) {
        return addSectionHandler.handle(cmd);
    }

    @PostMapping("/sections/assign-staff")
    public CommandResult<StaffAssignment> assignStaff(@RequestBody AssignStaffCommand cmd) {
        return assignStaffHandler.handle(cmd);
    }
}
