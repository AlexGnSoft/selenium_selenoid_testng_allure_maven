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
    private String emailTemplateName;
    private String clientName;
    private final String filePath = getResourcesPath() + "picture.png";

    @BeforeClass
    public void prepareClientData() {

        client = getTestClientByCode("MessageLevel client " + getRandomString());
        clientName = client.getName();
        messageName = getRandomString();
        emailTemplateName = getRandomString();
    }


    @Test(description = "Create MMS message with picture - Module Medication")
    public void createMmsWithPicture_MHM_T71() {
        //Test data
        String medicationProgram = "Aspirin & Blood Thinner Meds";
        String medicationName = getRandomString();
        String smsMessage = "Do not forget to take your pills";

        site.messagesSteps().addMedicationMmsMessage(Module.MEDICATION, Action.TIMED_ALERT, MessageType.MMS, messageName, medicationProgram, medicationName,  smsMessage, filePath);

        boolean isMmsWithPictureCreated = site.messagesSteps()
                .isMmsWithPictureCreated(clientName, messageName);

        Assert.assertTrue(isMmsWithPictureCreated,"The MMS message was not created!");
    }

    @Test(description = "Create sms message")
    public void createSmsMessage_MHM_T70() {
        String medicationName_T70 = getRandomString();
        site.messagesSteps()
                .addBiometricMedicationMessage(Module.BIOMETRIC, Action.TIMED_ALERT, MessageType.SMS, medicationName_T70, NotificationType.PAIN,
                        "${p} , tell us more about your symptoms level today.");

        boolean isMessageCreated = site.messagesSteps()
                .goToMessagesTab()
                .isMessageExist(clientName, medicationName_T70);

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

    @Test(description = "Create email template")
    public void createEmailTemplate_MHM_T87() {
        site.adminToolsSteps().goToSpecificTab("Email Templates");
        site.messagesSteps().addEmailTemplate(clientName, emailTemplateName, getRandomString());

        boolean isEmailTemplateCreated = site.messagesSteps()
                .goToEmailTemplatesTab()
                .isTemplateExist(clientName, emailTemplateName);

        Assert.assertTrue(isEmailTemplateCreated, "The email template was not created!");
    }

    @Test(description = "Create email message and send it", dependsOnMethods = "createEmailTemplate_MHM_T87")
    public void createEmailMessageAndSendIt_MHM_T84() {
        //Test data
        String customEmail = getRandomString()+"@gmail.com";
        String subject = getRandomString();
        String body = getRandomString();
        String emailMessageName = getRandomString();

        site.messagesSteps().addEmailMessage(Module.EDUCATION, MessageType.EMAIL, emailMessageName, customEmail, subject, body);

        boolean isMessageWasSent = site.messagesSteps().sendTestMessage(emailMessageName);

        Assert.assertTrue(isMessageWasSent,"The email message was not sent");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
