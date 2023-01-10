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
import org.testng.asserts.SoftAssert;

public class CampaignManagementTest extends AbstractCampaignLevelTest {

    private Client client;
    private String clientName;
    private Patient patient;
    private SoftAssert softAssert;
    private final String TO_ENDPOINT = PropertyFileReader.getVariableValue("twilioSmsSender");
    private final String filePath = getResourcesPath() + "picture.png";

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("CampaignLevel client " + getFormattedDate("dd-MM-yy-H-mm-ss"));
        clientName = client.getName();
        softAssert = new SoftAssert();
        patient = new Patient();
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Assign Campaign to Patient")
    public void assignCampaignToPatient_MHM_T107() {
        //Test data
        String messageName = getRandomString();
        String programName = "Program "+ getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setFirstName(getRandomString());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFieldsInner(patient);

        getTestBiometricMedicationMessage(messageName);
        site.campaignSteps()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER)
                .addCampaignToProgram(clientName, programName, Module.BIOMETRIC, campaignName)
                .addCampaignToPatient(patient, campaignName);

        boolean isCampaignAssignedToPatient = site.campaignSteps()
                .isCampaignAddedToPatient(campaignName);

        Assert.assertTrue(isCampaignAssignedToPatient, "Campaign was not assigned to Patient");
    }

    @Test(description = "Create campaign - Module Educational")
    public void createEducationalCampaign_MHM_T83() {
        //Test data
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();
        String messageName = getRandomString();
        getEmailTemplate(clientName);
        getTestEducationalEmailMessage(messageName);

        site.campaignSteps()
                .goToCampaignsTab()
                .addEducationalSurveyCampaign(clientName, Module.EDUCATION, campaignName, CampaignAccess.PUBLIC, campaignDescription, null);

        boolean isCampaignCreated = site.campaignSteps()
                .isCampaignExist(clientName, campaignName);

        Assert.assertTrue(isCampaignCreated, "Campaign was not created");
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

    @Test(description = "Create campaign - Module Biometric")
    public void createBiometricCampaign_MHM_T88() {
        //Test data
        String messageName = getRandomString();
        String programName = "Program "+ getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();

        getTestBiometricMedicationMessage(messageName);
        site.campaignSteps()
                .goToCampaignsTab()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER);

        boolean isCampaignCreated = site.campaignSteps()
                .isCampaignExist(clientName, campaignName);

        Assert.assertTrue(isCampaignCreated, "Campaign was not created");
    }

    @Test(description = "Assign Campaign to Program")
    public void assignCampaignToProgram_MHM_T104() {
        //Test data
        String messageName = getRandomString();
        String programName = "Program "+ getRandomString();
        String campaignName = getRandomString();
        String campaignDescription = getRandomString();

        getTestProgram(clientName, programName);
        getTestBiometricMedicationMessage(messageName);
        site.campaignSteps()
                .addBiometricAccountSettingCampaignScheduleProtocol(clientName, Module.BIOMETRIC, campaignName, CampaignAccess.PUBLIC, campaignDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.EVENT_DATE, programName, CampaignAdjustDate.NEXT_FRIDAY, CampaignDays.AFTER)
                .addCampaignToProgram(clientName, programName, Module.BIOMETRIC, campaignName);

        boolean isCampaignAddedToProgram = site.campaignSteps()
                .isCampaignAddedToProgram(campaignName);

        boolean isSameCampaignCannotBeAddedTwice = site.campaignSteps()
                .isSameCampaignCannotBeAddedTwice(Module.BIOMETRIC, campaignName);

        softAssert.assertTrue(isCampaignAddedToProgram, "Campaign was not created");
        softAssert.assertTrue(isSameCampaignCannotBeAddedTwice, "Same campaign could be added to program twice");
    }

    @Test(description = "Create campaign - Module Account settings") //excluded this test, as it fails on Jenkins, and works Locally
    public void createAccountSettingsCampaign_MHM_T99() {
        //Test data
        String campaignLocation = "America/New_York";
        String programName = getRandomString();
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

        Assert.assertEquals(campaignMessage, actualLasLogsMessage, "Campaign message did not arrive to patient");
    }

    @Test(description = "Remove campaign from patient")
    public void removeCampaignFromPatient_MHM_T109() {
        //Test data
        String campaignLocation = "America/New_York";
        String campaignNameDescription = getRandomString();
        String programName = "Remove campaign test patient "+ getRandomString();
        String campaignMessage = "Do not forget to take your pills";
        String SIGN_UP_KEYWORD = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addKeywordForSignUp(SIGN_UP_KEYWORD);

        site.campaignSteps().addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(false, clientName, "Account Settings", campaignNameDescription, CampaignAccess.PUBLIC, campaignNameDescription, "Occasion",
                        "Birth Date", campaignMessage,
                        "Wrong birth day data format, it should be like, mm/dd/yyyy", campaignLocation)
                .addCampaignToProgram(clientName, programName, "Account Settings", campaignNameDescription);

        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD);

        site.campaignSteps().removeCampaignFromPatient(campaignNameDescription);

        boolean isCampaignDeletedFromPatient = site.campaignSteps()
                .isCampaignDeletedFromPatient(campaignNameDescription);

        boolean isSameCampaignCanBeAddedToPatientAfterDeletion = site.campaignSteps()
                .isSameCampaignCanBeAddedToPatientAfterDeletion(campaignNameDescription);

        softAssert.assertTrue(isCampaignDeletedFromPatient, "Campaign was not deleted from Patient");
        softAssert.assertTrue(isSameCampaignCanBeAddedToPatientAfterDeletion, "Removed campaign could not be added again. This is an error!");
    }
    @Test(description = "Remove campaign from program")
    public void removeCampaignFromProgram_MHM_T110() {
        //Test data
        String campaignLocation = "America/New_York";
        String campaignNameDescription = getRandomString();
        String programName = "Remove campaign test program "+ getRandomString();
        String campaignMessage = "Do not forget to take your pills";
        String SIGN_UP_KEYWORD = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addKeywordForSignUp(SIGN_UP_KEYWORD);

        site.campaignSteps().addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(false, clientName, "Account Settings", campaignNameDescription, CampaignAccess.PUBLIC, campaignNameDescription, "Occasion",
                        "Birth Date", campaignMessage,
                        "Wrong birth day data format, it should be like, mm/dd/yyyy", campaignLocation)
                .addCampaignToProgram(clientName, programName, "Account Settings", campaignNameDescription);

        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD);

        site.campaignSteps()
                .removeCampaignFromProgram(campaignNameDescription)
                .goToPatientMedCampaignsTab(FROM_PHONE_NUMBER);

        boolean isCampaignDeletedFromPatient = site.campaignSteps().isCampaignDeletedFromPatient(campaignNameDescription);
        softAssert.assertTrue(isCampaignDeletedFromPatient, "Campaign was not deleted from Program");

        site.campaignSteps().addCampaignToProgram(clientName, programName, "Account Settings", campaignNameDescription);
        boolean isCampaignAddedToProgramAfterDeletion = site.campaignSteps().isCampaignAddedToProgram(campaignNameDescription);
        softAssert.assertTrue(isCampaignAddedToProgramAfterDeletion, "Removed campaign could not be added to program again. This is an error!");
    }

    @Test(description = "Allocate multiple messages to campaign and remove them")
    public void allocateMultipleMessagesToCampaignRemoveMessages_MHM_T100() {
        //Test data
        String campaignLocation = "America/New_York";
        String messageName1 = getRandomString();
        String messageName2 = getRandomString();
        String medicationProgram = "Aspirin & Blood Thinner Meds";
        String medicationName = getRandomString();
        String campaignNameDescription = getRandomString();
        String campaignMessage = "Do not forget to take your pills";

        site.messagesSteps()
                .addMedicationMmsMessage(Module.MEDICATION, Action.TIMED_ALERT, MessageType.MMS, messageName1, medicationProgram, medicationName,  campaignMessage)
                .addMedicationMmsMessage(Module.MEDICATION, Action.TIMED_ALERT, MessageType.MMS, messageName2, medicationProgram, medicationName,  campaignMessage);

        site.campaignSteps().addMedicationCampaignScheduleProtocolWithSeveralMessages(clientName, Module.MEDICATION, campaignNameDescription, CampaignAccess.PUBLIC, campaignNameDescription, CampaignScheduleType.PROTOCOL, CampaignAnchor.FIXED_DATE, campaignLocation);

        boolean areMessagesAddedToCampaign = site.campaignSteps().areMessagesAddedToCampaign(2);
        boolean areAllMessagesRemoved = site.campaignSteps().removeAllMessagesFromCampaign();

        softAssert.assertTrue(areMessagesAddedToCampaign, "Multiple messages were not added to campaign");
        softAssert.assertTrue(areAllMessagesRemoved, "Multiple messages were not deleted to campaign");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
