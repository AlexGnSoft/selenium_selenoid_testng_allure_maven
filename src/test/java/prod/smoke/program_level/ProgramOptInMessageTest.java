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
    public void addOptInMessageWithConfirmation_MHM_T165() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, true)
                .addNewPatient(patient, client, programName);

        String expectedOptInMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);

        MessageLogItem actualOptInMessage = site.programSteps().getLastPatientMessage(patient);

        Assert.assertEquals(actualOptInMessage.getMessage(), expectedOptInMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Add opt-in message with 'Do NOT send opt in confirmation message' checkbox", dependsOnMethods = "addOptInMessageWithConfirmation_MHM_T165")
    public void addOptInMessageWithoutConfirmation_MHM_T32() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, false);

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Agree");

        Assert.assertEquals(actualOptInMessage.getMessage(), "Agree", "Received message is not the same as expected!");
    }

    @Test(description = "Check image is attached opt-in message", dependsOnMethods = {"addOptInMessageWithConfirmation_MHM_T165", "addOptInMessageWithoutConfirmation_MHM_T19"})
    public void messageAttachment_MHM_T164() {
        boolean isImageAttached = site.programSteps().isAttachedImageDisplayed();

        Assert.assertTrue(isImageAttached, "Image is not attached");
    }

    @Test(description = "Simulate patient's AGREE response", dependsOnMethods = {"addOptInMessageWithConfirmation_MHM_T165", "addOptInMessageWithoutConfirmation_MHM_T19", "messageAttachment_MHM_T164"} )
    public void simulateConfirmation_MHM_T44() {
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

        MessageLogItem actualOptInMessage = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient2, "Agree");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response", dependsOnMethods = "simulateConfirmation_MHM_T44")
    public void simulateStop_MHM_T46() {

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Stop");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response", dependsOnMethods = "simulateStop_MHM_T46")
    public void simulateStart_MHM_T166() {

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "Start");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response", dependsOnMethods = "simulateStart_MHM_T166")
    public void simulateHelp_MHM_T47() {

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
