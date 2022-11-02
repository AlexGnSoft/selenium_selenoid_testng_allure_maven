package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;

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

    /**
     * Create a message for a specific Module, Action and Type
     *
     * @param module             module
     * @param action             action
     * @param messageType        message type
     * @param notificationType   notification type
     * @param smsMessage         body of the sms message
     * @return MessagesSteps object
     */
    MessagesSteps addSmsMessage(Module module, Action action, MessageType messageType, String messageName, NotificationType notificationType, String smsMessage);

    /**
     * Verify that message is created
     *
     * @param clientName     client name
     * @param messageName    message name
     * @return return true if message is found, otherwise false
     */
    boolean isMessageCreated(String clientName, String messageName);

    /**
     * Verify that message is created and displayed
     *
     * @param messageName    message name
     * @return return true if message is found, otherwise false
     */
    boolean isCreatedMessageDisplayed(String messageName);

    /**
     * Get text of the created message
     * @return return String which are body of the message
     */
    String getMessageText();

    /**
     * Verify that message is created and displayed
     * @param newMessage    new text of a message
     *
     * @return return true if message is found, otherwise false
     */
    MessagesSteps updateTextMessageBody(String newMessage);

    /**
     * Verify that messages is equal
     *
     * @param initialMessage    new text of a message
     * @param expectedUpdatedMessage    new text of a message
     *
     * @return return true if message is updated, otherwise false
     */
    boolean areMessageTextUpdated(String initialMessage, String expectedUpdatedMessage);

}
