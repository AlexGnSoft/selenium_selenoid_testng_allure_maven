package prod.smoke.message_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MessageManagementTest extends AbstractMessageLeveTest{

    private Client client;
    private String messageName;
    private String clientName;

    @BeforeClass
    public void prepareClientData() {

        client = getTestClientByCode("MessageLevel client");
        clientName = client.getName();
        messageName = getRandomString();
    }

    @Test(description = "Create sms message")
    public void createSmsMessage_MHM_T70() {
        site.messagesSteps()
                .addSmsMessage(Module.BIOMETRIC, Action.TIMED_ALERT, MessageType.SMS, messageName, NotificationType.PAIN,
                        "${p} , tell us more about your symptoms level today.");

        boolean isMessageCreated = site.messagesSteps()
                .goToMessagesTab()
                .isMessageCreated(clientName, messageName);

        Assert.assertTrue(isMessageCreated,"The message was not created!");
    }

    @Test(description = "Edit text of sms message", dependsOnMethods = "createSmsMessage_MHM_T70")
    public void editSmsMessage_MHM_T81() {
        String initialMessage = site.messagesSteps().getMessageText();
        site.messagesSteps().updateTextMessageBody("Updated message");
        String updatedMessage = site.messagesSteps().getMessageText();

        boolean areMessagesEqual = site.messagesSteps().areMessageTextUpdated(initialMessage, updatedMessage);

        Assert.assertTrue(areMessagesEqual, "The message text was not updated!");
    }


    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }

}
