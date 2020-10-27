package com.carespeak.domain.steps;

import com.carespeak.domain.entities.message.Module;

import java.util.List;

public interface MessagesSteps extends BaseSteps {

    MessagesSteps goToMessagesTab();

    /**
     * Retrieves available modules for provided client
     *
     * @param clientName - client code
     * @return ProgramSteps object
     */
    List<Module> getAvailableModules(String clientName);
}
