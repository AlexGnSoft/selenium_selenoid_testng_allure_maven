package prod.smoke.campaign_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalTime;

public class CampaignManagementTest extends AbstractCampaignLevelTest {

    private Client client;
    private String clientName;
    private SoftAssert softAssert;
    private final String TO_ENDPOINT = PropertyFileReader.getVariableValue("twilioSmsSender");

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("CampaignLevel client " + getRandomString());
        clientName = client.getName();
        getEmailTemplate(clientName);
        softAssert = new SoftAssert();
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
        String messageName = getRandomString();
        String programName = getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();

        getTestBiometricMedicationMessage(messageName);
        getTestProgram(clientName, programName);

        site.campaignSteps()
               .goToCampaignsTab()
               .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER,
        null);

        boolean isCampaignCreated = site.campaignSteps()
                .isCampaignExist(clientName, campaignName);

        Assert.assertTrue(isCampaignCreated, "Campaign was not created");
    }

    @Test(description = "Assign Campaign to Program")
    public void assignCampaignToProgram_MHM_T104() {
        //Test data
        String messageName = getRandomString();
        String programName = getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();

        getTestProgram(clientName, programName);
        getTestBiometricMedicationMessage(messageName);
        site.campaignSteps()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER,
                        null)
                .addCampaignToProgram(clientName, programName, Module.BIOMETRIC, campaignName);

        boolean isCampaignAddedToProgram = site.campaignSteps()
                .isCampaignAddedToProgram(campaignName);

        boolean isSameCampaignCannotBeAddedTwice = site.campaignSteps()
                        .isSameCampaignCannotBeAddedTwice(Module.BIOMETRIC, campaignName);

        softAssert.assertTrue(isCampaignAddedToProgram, "Campaign was not created");
        softAssert.assertTrue(isSameCampaignCannotBeAddedTwice, "Same campaign could be added to program twice");
    }

    @Test(description = "Create campaign - Module Account settings")
    public void createAccountSettingsCampaign_MHM_T99() {
        String programName = getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();
        String campaignMessage = "When is you birthday?";
        String SIGN_UP_KEYWORD_T99 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();
        Patient patient = new Patient();
        patient.setFirstName("AutoName"+getRandomString());
        patient.setLastName("Automator");
        getTestProgram(clientName, programName);

        site.programSteps().goToProgramSettings(clientName, programName).addKeywordForSignUp(SIGN_UP_KEYWORD_T99);
        site.campaignSteps().addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(false, clientName, "Account Settings", campaignName, CampaignAccess.PUBLIC, campaignDescription, "Occasion",
                "Birth Date", campaignMessage,
                "Wrong birth day data format, it should be like, mm/dd/yyyy")
                .addAccountSettingCampaignToProgram(clientName, programName, "Account Settings", campaignName);

        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T99);

        String actualLasLogsMessage = site.campaignSteps().doesCampaignMessageArrive(campaignMessage, FROM_PHONE_NUMBER);

        Assert.assertEquals(campaignMessage, actualLasLogsMessage, "Campaign message did not arrive to patient");
    }

    @Test(description = "Create campaign - Module Medication")
    public void createMedicationCampaign_MHM_T89() {

    }




    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
