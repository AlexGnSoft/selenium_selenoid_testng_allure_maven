package prod.smoke.program_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProgramManagementTest extends AbstractProgramLevelTest {

    private Client client;
    private String clientName;
    private Patient patient;
    private SoftAssert softAssert;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTest client " + getFormattedDate("dd-MM-yy-H-mm"));
        clientName = client.getName();
        patient = new Patient();
        patient.setFirstName("PatientFirstName" + getRandomString());
        patient.setLastName("PatientLastName" + getRandomString());
        patient.setEmail(getRandomString()+"@optimizerx.com");
        patient.setZipCode("77777");
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setTimezone("Eastern Time (New York)");
        patient.setMonthOfBirth("March");
        patient.setDayOfBirth("5");
        patient.setYearOfBirth("1980");
        patient.setSex(Sex.MALE);
        softAssert = new SoftAssert();
    }

    @Test(description = "Add new program")
    public void addNewProgram_MHM_T25() {
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");

        site.programSteps().addNewProgram(clientName, programName, ProgramAccess.PUBLIC);

        String actualProgramName = site.programSteps()
                .getProgramByName(clientName, programName);
        Assert.assertEquals(actualProgramName, programName, "Actual program differs from expected program");
    }

    @Test(description = "Check that program is set to public")
    public void publicProgram_MHM_T168() {
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");

        site.programSteps().addNewProgram(clientName, programName, ProgramAccess.PUBLIC);

        String actualAccess = site.programSteps()
                .getProgramAccessModifier(clientName, ProgramAccess.PUBLIC.getValue() + " (Visit here)");

        Assert.assertEquals(actualAccess, ProgramAccess.PUBLIC.getValue(), "Actual access differs from expected access");
    }

    @Test(description = "Add new patient manually") //excluded this test, as it fails on Jenkins, and works Locally
    public void addPatientManually_MHM_T114(){
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setFirstName("ProgramManagementTestPatient-" + getRandomString());

        site.programSteps().addNewProgram(clientName, programName, ProgramAccess.PUBLIC);
        site.programSteps().addNewPatientLimitedFields(patient, client, programName);

        String actualPatientName = site.programSteps()
                .getPatientByName(clientName, programName, patient.getFirstName());

        Assert.assertEquals(actualPatientName, patient.getFirstName(), "Actual patient differs from expected patient");
    }

    @Test(description = "Delete patient from program")
    public void deletePatientFromProgram_MHM_T143(){
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setFirstName("ProgramManagementTestPatient-" + getRandomString());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFields(patient, client, programName)
                .removePatient(patient.getFirstName());

        boolean isPatientRemovedFromUsersPage = site.programSteps()
                .isPatientRemovedFromUsersPage(patient.getFirstName());

        Assert.assertTrue(isPatientRemovedFromUsersPage, "Patient was not deleted from Users page");
    }
    @Test(description = "Edit patient - Update patient profile 'Details'")
    public void updatePatientStatusPatientProfile_MHM_T120(){
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        Patient newPatient = new Patient();
        newPatient.setFirstName("PatientUpdatedFirstName" + getRandomString());
        newPatient.setLastName("PatientUpdatedLastName" + getRandomString());
        newPatient.setEmail(getRandomString()+"@gmail.com");
        newPatient.setZipCode("55555");
        newPatient.setCellPhone(getGeneratedPhoneNumber());
        newPatient.setTimezone("Eastern Time (New York)");
        newPatient.setMonthOfBirth("April");
        newPatient.setDayOfBirth("1");
        newPatient.setYearOfBirth("2021");

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientAllFields(patient, client, programName)
                .updatePatientAllFields(newPatient);

        Patient actualPatient = new Patient();
        site.programSteps().getPatientObject(actualPatient);

        boolean equality = !patient.equals(actualPatient);

        Assert.assertTrue(equality, "Patient details were are not updated");
    }
    @Test(description = "Edit patient - Change patient status in Patient profile")
    public void changePatientStatusPatientProfile_MHM_T142(){
        //Test data
        String patientNewStatus = "Stopped";
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setFirstName("ProgramManagementTestPatient-" + getRandomString());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFields(patient, client, programName)
                .updatePatientStatusShort(patient, patientNewStatus);

        boolean isPatientStatusWasUpdated = site.programSteps().isStatusUpdatedOnMessageLogsPage(patientNewStatus);

        softAssert.assertTrue(isPatientStatusWasUpdated, "Patient status was not updated on Message Logs / Program patients page");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
