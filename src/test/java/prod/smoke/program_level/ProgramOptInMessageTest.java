package prod.smoke.program_level;

import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramOptInMessageTest extends AbstractProgramLevelTest {

    private final String filePath = getResourcesPath() + "picture.png";

    private Client client;
    private String clientName;
    private Patient patient;


    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestOptIn client");
        clientName = client.getName();
        patient = new Patient();
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add opt-in message")
    public void addOptInMessageWithConfirmation_MHM_T165() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, true)
                .addNewPatient(patient, client, programName);

        String expectedOptInMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);

        MessageLogItem actualOptInMessage = site.programSteps().getLastPatientMessage(patient);

        Assert.assertEquals(actualOptInMessage.getMessage(), expectedOptInMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Add opt-in message with 'Do NOT send opt in confirmation message' checkbox")
    public void addOptInMessageWithoutConfirmation_MHM_T32() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, false)
                .addNewPatient(patient, client, programName);

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        Assert.assertEquals(actualOptInMessage.getMessage(), "AGREE", "Received message is not the same as expected!");
    }

    @Test(description = "Check image is attached opt-in message", dependsOnMethods = "addOptInMessageWithoutConfirmation_MHM_T32")
    public void messageAttachment_MHM_T164() {
        boolean isImageAttached = site.programSteps().isAttachedImageDisplayed();

        Assert.assertTrue(isImageAttached, "Image is not attached");
    }

    @Test(description = "Simulate patient's AGREE response")
    public void simulateConfirmation_MHM_T44() {
        //TODO: Page refresh to make 'Programs' button visible
        site.programSteps().pageRefresh();
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addNewPatient(patient, client, programName);

        MessageLogItem actualOptInMessage = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response")
    public void simulateStop_MHM_T46() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "STOP");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response")
    public void simulateStart_MHM_T166() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");
        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "STOP");


        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "START");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response")
    public void simulateHelp_MHM_T47() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .goToProgramSettings(client.getName(), programName)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "HELP");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.HELP, "Received message is not the same as expected!");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
