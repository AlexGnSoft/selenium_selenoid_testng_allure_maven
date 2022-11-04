package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;

import java.util.List;

public interface MessagesSteps extends BaseSteps {

    MessagesSteps goToMessagesTab();

    MessagesSteps goToEmailTemplatesTab();

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
     * Create a message for a specific Module, Action and Type
     *
     * @param module             module
     * @param messageType        message type
     * @param notificationType   notification type
     * @param smsMessage         body of the sms message
     * @return MessagesSteps object
     */
    MessagesSteps addEmailMessage(Module module, MessageType messageType, String messageName, NotificationType notificationType, String smsMessage);

    /**
     * Verify that message is created
     *
     * @param clientName     client name
     * @param messageName    message name
     * @return return true if message is found, otherwise false
     */
    boolean isMessageExist(String clientName, String messageName);

    /**
     * Verify that template is created
     *
     * @param clientName     client name
     * @param templateName    template name
     * @return return true if message is found, otherwise false
     */
    boolean isTemplateExist(String clientName, String templateName);

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
     *
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

    /**
     * Create a message for a specific Module, Action and Type
     *
     * @param clientName         client name for which a template will be created
     * @param templateName       name of template
     * @param templateBody       body of template
     * @return MessagesSteps object
     */
    MessagesSteps addEmailTemplate(String clientName, String templateName, String templateBody);

}
