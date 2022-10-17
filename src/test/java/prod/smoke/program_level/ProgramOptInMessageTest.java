package prod.smoke.program_level;

import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramOptInMessageTest extends AbstractProgramLevelTest {

    private final String filePath = getResourcesPath() + "picture.png";

    private Client client;
    private Patient patient;
    private String programName;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestOptIn client");
        programName = getTestProgramByName("OptIn program " + getRandomString(), client);
        patient = new Patient();
        patient.setFirstName("Patient " + getRandomString());
        patient.setSex(Sex.MALE);
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add opt-in message")
    public void addOptInMessageWithConfirmation() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, true)
                .addNewPatient(patient, client, programName);

        String expectedOptInMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);
        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), expectedOptInMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Add opt-in message with 'Do NOT send opt in confirmation message' checkbox", dependsOnMethods = "addOptInMessageWithConfirmation")
    public void addOptInMessageWithoutConfirmation() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, false);

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Agree");

        Assert.assertEquals(actualOptInMessage.getMessage(), "Agree", "Received message is not the same as expected!");
    }

    @Test(description = "Check image is attached opt-in message", dependsOnMethods = {"addOptInMessageWithConfirmation", "addOptInMessageWithoutConfirmation"})
    public void messageAttachment() {
        boolean isImageAttached = site.programSteps().isAttachedImageDisplayed();

        Assert.assertTrue(isImageAttached, "Image is not attached");
    }

    @Test(description = "Simulate patient's AGREE response", dependsOnMethods = {"addOptInMessageWithConfirmation", "addOptInMessageWithoutConfirmation", "messageAttachment"} )
    public void simulateConfirmation() {
        //TODO: Page refresh to make 'Programs' button visible
        site.programSteps().pageRefresh();
        //TODO: Creating a new program and patient (new patient is needed for this test)
        String programName2 = getTestProgramByName("OptIn program " + getRandomString(), client);
        Patient patient2 = new Patient();
        patient2.setFirstName("Patient2 " + getRandomString());
        patient2.setSex(Sex.MALE);
        patient2.setCellPhone(getGeneratedPhoneNumber());
        patient2.setTimezone("Eastern Time (New York)");

        site.programSteps().addNewPatient(patient2, client, programName2);

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient2, "Agree");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response", dependsOnMethods = "simulateConfirmation")
    public void simulateStop() {

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Stop");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response", dependsOnMethods = "simulateStop")
    public void simulateStart() {

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Start");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response", dependsOnMethods = "simulateStart")
    public void simulateHelp() {

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Help");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.HELP, "Received message is not the same as expected!");
    }

    @AfterClass(alwaysRun = true)
    public void removeProgram() {
        site.programSteps().pageRefresh();
        site.programSteps().removeProgram(client, programName);
        List<String> programs = site.programSteps().getProgramsForClient(client);
        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
    }
}
