package prod.smoke.campaign_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CampaignManagementTest extends AbstractCampaignLevelTest {

    private Client client;
    private String clientName;
    private SoftAssert softAssert;
    private final String TO_ENDPOINT = PropertyFileReader.getVariableValue("twilioSmsSender");
    private final String filePath = getResourcesPath() + "picture.png";

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("CampaignLevel client " + getRandomString());
        clientName = client.getName();
        getEmailTemplate(clientName);
        softAssert = new SoftAssert();
    }

    @Test(description = "Create campaign - Module Medication")
    public void createMedicationCampaign_MHM_T89() {
        //Test data
        String messageName = getRandomString();
        String medicationProgram = "Aspirin & Blood Thinner Meds";
        String medicationName = getRandomString();
        String campaignNameDescription = getRandomString();
        String programName_T89 = "Medication program "+ getRandomString();
        String campaignMessage_T89 = "Do not forget to take your pills";
        String SIGN_UP_KEYWORD_T89 = getRandomString();
        String FROM_PHONE_NUMBER_T89 = getGeneratedPhoneNumber();

        site.messagesSteps().addMedicationMmsMessage(Module.MEDICATION, Action.TIMED_ALERT, MessageType.MMS, messageName, medicationProgram, medicationName,  campaignMessage_T89, filePath);
        site.campaignSteps().addMedicationCampaignScheduleProtocol(clientName, Module.MEDICATION, campaignNameDescription, CampaignAccess.PUBLIC, campaignNameDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.FIXED_DATE);
        getTestProgram(clientName, programName_T89);
        site.programSteps().addKeywordForSignUp(SIGN_UP_KEYWORD_T89);
        site.campaignSteps().addCampaignToProgram(clientName, programName_T89, Module.MEDICATION, campaignNameDescription);
        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName_T89, FROM_PHONE_NUMBER_T89, TO_ENDPOINT, SIGN_UP_KEYWORD_T89);
        String actualLasLogsMessage = site.campaignSteps().didCampaignMessageArrive(campaignMessage_T89, FROM_PHONE_NUMBER_T89);

        Assert.assertEquals(actualLasLogsMessage, campaignMessage_T89, "Campaign message did not arrive to patient");
    }

    @Test(description = "Create campaign - Module Educational")
    public void createEducationalCampaign_MHM_T83() {
        //Test data
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();
        String messageName = getRandomString();

        getTestEducationalEmailMessage(messageName);

        site.campaignSteps()
                .goToCampaignsTab()
                .addEducationalSurveyCampaign(clientName, Module.EDUCATION, campaignName, CampaignAccess.PUBLIC, campaignDescription, null);

        boolean isCampaignCreated = site.campaignSteps()
                .isCampaignExist(clientName, campaignName);

        Assert.assertTrue(isCampaignCreated, "Campaign was not created");
    }

    @Test(description = "Create campaign - Module Biometric")
    public void createBiometricCampaign_MHM_T88() {
        //Test data
        String messageName_MHM_T88 = getRandomString();
        String programName_MHM_T88 = getRandomString();
        String campaignName_MHM_T88 = getRandomString();
        String campaignDescription = getRandomString();

        getTestBiometricMedicationMessage(messageName_MHM_T88);
        getTestProgram(clientName, programName_MHM_T88);

        site.campaignSteps()
                .goToCampaignsTab()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName_MHM_T88, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName_MHM_T88, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER,
                        null);

        boolean isCampaignCreated = site.campaignSteps()
                .isCampaignExist(clientName, campaignName_MHM_T88);

        Assert.assertTrue(isCampaignCreated, "Campaign was not created");
    }

    @Test(description = "Assign Campaign to Program")
    public void assignCampaignToProgram_MHM_T104() {
        //Test data
        String messageName_MHM_T104 = getRandomString();
        String programName_MHM_T104 = getRandomString();
        String campaignName_MHM_T104 = getRandomString();
        String campaignDescription = getRandomString();

        getTestProgram(clientName, programName_MHM_T104);
        getTestBiometricMedicationMessage(messageName_MHM_T104);
        site.campaignSteps()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName_MHM_T104, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName_MHM_T104, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER,
                        null)
                .addCampaignToProgram(clientName, programName_MHM_T104, Module.BIOMETRIC, campaignName_MHM_T104);

        boolean isCampaignAddedToProgram = site.campaignSteps()
                .isCampaignAddedToProgram(campaignName_MHM_T104);

        boolean isSameCampaignCannotBeAddedTwice = site.campaignSteps()
                .isSameCampaignCannotBeAddedTwice(Module.BIOMETRIC, campaignName_MHM_T104);

        softAssert.assertTrue(isCampaignAddedToProgram, "Campaign was not created");
        softAssert.assertTrue(isSameCampaignCannotBeAddedTwice, "Same campaign could be added to program twice");
    }

    @Test(description = "Create campaign - Module Account settings")
    public void createAccountSettingsCampaign_MHM_T99() {
        String programName_MHM_T99 = getRandomString();
        String campaignName_MHM_T99 = getRandomString();
        String campaignDescription = getRandomString();
        String campaignMessage_T99 = "When is you birthday?";
        String SIGN_UP_KEYWORD_T99 = getRandomString();
        String FROM_PHONE_NUMBER_T99 = getGeneratedPhoneNumber();
        Patient patient = new Patient();
        patient.setFirstName("AutoName " + getRandomString());
        patient.setLastName("Automator");
        getTestProgram(clientName, programName_MHM_T99);

        site.programSteps().goToProgramSettings(clientName, programName_MHM_T99).addKeywordForSignUp(SIGN_UP_KEYWORD_T99);
        site.campaignSteps().addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(false, clientName, "Account Settings", campaignName_MHM_T99, CampaignAccess.PUBLIC, campaignDescription, "Occasion",
                        "Birth Date", campaignMessage_T99,
                        "Wrong birth day data format, it should be like, mm/dd/yyyy")
                .addCampaignToProgram(clientName, programName_MHM_T99, "Account Settings", campaignName_MHM_T99);

        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName_MHM_T99, FROM_PHONE_NUMBER_T99, TO_ENDPOINT, SIGN_UP_KEYWORD_T99);

        String actualLasLogsMessage = site.campaignSteps().didCampaignMessageArrive(campaignMessage_T99, FROM_PHONE_NUMBER_T99);

        Assert.assertEquals(campaignMessage_T99, actualLasLogsMessage, "Campaign message did not arrive to patient");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
