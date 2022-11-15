package prod.smoke.campaign_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;
import com.carespeak.domain.entities.program.ProgramAccess;
import prod.base.BaseTest;

/**
 * Abstract class to be extended by all Campaign level tests.
 * Preparations that common for all campaign level tests should be implemented here.
 */
public class AbstractCampaignLevelTest extends BaseTest {
    protected Client getTestClientByCode(String clientCode) {
        String twilioSmsSenderEndPoint = PropertyFileReader.getVariableValue("twilioSmsSender");
        return getTestClientByCode(clientCode, twilioSmsSenderEndPoint);
    }

    protected Client getTestClientByCode(String clientCode, String endpoint) {
        Client client = site.adminToolsSteps().getClientByCode(clientCode);
        if (client != null) {
            return client;
        }
        Client newClient = new Client();
        newClient.setName(clientCode);
        newClient.setModules(Module.CHECK_ALL);
        newClient.setCode(clientCode);
        newClient.setEndpoint(endpoint);
        site.adminToolsSteps().addNewClient(newClient, Module.CHECK_ALL);
        return newClient;
    }

    protected void getTestEducationalEmailMessage (String messageName){
        site.messagesSteps().addEmailMessage(Module.EDUCATION, MessageType.EMAIL, messageName, "testeremail@gmail.com", "testsubject", "testbody");
    }

    protected void getTestBiometricMedicationMessage (String messageName){
        site.messagesSteps()
                .addBiometricMedicationMessage(Module.BIOMETRIC, Action.TIMED_ALERT, MessageType.SMS, messageName, NotificationType.PAIN,
                "${p} , tell us more about your symptoms level today.");
    }

    protected void getTestProgram(String clientName, String programName){
        site.programSteps().addNewProgram(clientName, programName, ProgramAccess.PUBLIC);
    }

    protected void getEmailTemplate (String clientName){
        site.adminToolsSteps().goToSpecificTab("Email Templates");
        site.messagesSteps().addEmailTemplate(clientName, getRandomString(), getRandomString());
    }
}
