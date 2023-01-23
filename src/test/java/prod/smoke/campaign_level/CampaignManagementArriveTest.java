package prod.smoke.campaign_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CampaignManagementArriveTest extends AbstractCampaignLevelTest {

    private Client client;
    private String clientName;
    private Patient patient;
    private final String TO_ENDPOINT = PropertyFileReader.getVariableValue("twilioSmsSender");
    private final String filePath = getResourcesPath() + "picture.png";

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("CampaignArriveLevel client " + getFormattedDate("dd-MM-yy-H-mm-ss"));
        clientName = client.getName();
        patient = new Patient();
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Create campaign - Module Medication")
    public void createMedicationCampaign_MHM_T89() {
        //Test data
        String campaignLocation = "America/New_York";
        String messageName = getRandomString();
        String medicationProgram = "Aspirin & Blood Thinner Meds";
        String medicationName = getRandomString();
        String campaignNameDescription = getRandomString();
        String programName = "Medication program "+ getRandomString();
        String campaignMessage = "Do not forget to take your pills";
        String SIGN_UP_KEYWORD = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        site.messagesSteps().addMedicationMmsMessageWithAttachment(Module.MEDICATION, Action.TIMED_ALERT, MessageType.MMS, messageName, medicationProgram, medicationName,  campaignMessage, filePath);
        site.campaignSteps().addMedicationCampaignScheduleProtocol(clientName, Module.MEDICATION, campaignNameDescription, CampaignAccess.PUBLIC, campaignNameDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.FIXED_DATE, campaignLocation);
        getTestProgram(clientName, programName);
        site.programSteps().addKeywordForSignUp(SIGN_UP_KEYWORD);
        site.campaignSteps().addCampaignToProgram(clientName, programName, Module.MEDICATION, campaignNameDescription);
        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD);

        String actualLasLogsMessage = site.campaignSteps().didCampaignMessageArrive(campaignMessage, FROM_PHONE_NUMBER);

        Assert.assertEquals(actualLasLogsMessage, campaignMessage, "Campaign message did not arrive to patient");
    }

    @Test(description = "Create campaign - Module Account settings") //excluded this test, as it fails on Jenkins, and works Locally
    public void createAccountSettingsCampaign_MHM_T99() {
        //Test data
        String campaignLocation = "America/New_York";
        String programName = "Program "+ getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();
        String campaignMessage = "When is you birthday?";
        String SIGN_UP_KEYWORD = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();
        Patient patient = new Patient();
        patient.setFirstName("AutoName " + getRandomString());
        patient.setLastName("Automator");

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addKeywordForSignUp(SIGN_UP_KEYWORD);

        site.campaignSteps().addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(false, clientName, "Account Settings", campaignName, CampaignAccess.PUBLIC, campaignDescription, "Occasion",
                        "Birth Date", campaignMessage,
                        "Wrong birth day data format, it should be like, mm/dd/yyyy", campaignLocation)
                .addCampaignToProgram(clientName, programName, "Account Settings", campaignName);

        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD);

        String actualLasLogsMessage = site.campaignSteps().didCampaignMessageArrive(campaignMessage, FROM_PHONE_NUMBER);
        boolean bol = campaignMessage.equals(actualLasLogsMessage);

        Assert.assertTrue(bol, "Campaign message did not arrive to patient");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
