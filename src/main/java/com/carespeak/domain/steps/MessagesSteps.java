package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;

import java.util.List;

public interface MessagesSteps extends BaseSteps {

    MessagesSteps goToMessagesTab();

    /**
     * Retrieves available modules for provided client
     *
     * @param clientName client code
     * @return ProgramSteps object
     */
    List<Module> getAvailableModules(String clientName);

    /**
     * Verify equality of available list of Modules with a list of Modules selected in Client settings
     *
     * @param modules array of modules
     * @param client client object
     * @return ProgramSteps object
     */
    Boolean isMessageModulesEqualToClient(Module[] modules, Client client);
}
