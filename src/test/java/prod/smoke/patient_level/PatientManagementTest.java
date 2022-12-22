package prod.smoke.patient_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PatientManagementTest extends AbstractPatientLevelTest {

    private Client client;
    private String clientName;
    private Patient patient;
    private SoftAssert softAssert;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("PatientLevelTest client " + getFormattedDate("dd-MM-yy-H-mm"));
        clientName = client.getName();
        patient = new Patient();
        patient.setTimezone("Eastern Time (New York)");
        softAssert = new SoftAssert();
    }

    @Test(description = "Create patient list")
    public void createPatientList_MHM_T150(){
        //Test data
        String patientListName = "Patient list name " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("PatientManagementTestPatient-" + getRandomString());

        site.patientSteps().addPatientList(clientName, patientListName);

        boolean isPatientListCreated = site.patientSteps().isPatientListCreated(patientListName);

        Assert.assertTrue(isPatientListCreated, "Patient list was not created");
    }

    @Test(description = "Add one patient to a patient list")
    public void addPatientToList_MHM_T151(){
        //Test data
        String programName = "Patient program " + getFormattedDate("dd-MM-yy-H-mm");
        String patientListName = getRandomString();
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.patientSteps().addPatientList(clientName, patientListName);

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFieldsInner(patient)
                .addPatientToList(patient.getFirstName(), patientListName);

        boolean isPatientAddedToPatientList =
                site.patientSteps().isPatientAddedToPatientList(patient.getCellPhone(), patient.getFirstName());

        Assert.assertTrue(isPatientAddedToPatientList, " Patient was not added to list");
    }

    @Test(description = "Add multiple patients to a patient list")
    public void addMultiplePatientsToList_MHM_T152(){
        //Test data
        String programName = "Patient program " + getFormattedDate("dd-MM-yy-H-mm");
        int numberOfPatients = 2;
        String patientListName = getRandomString();
        patient.setCellPhone(getGeneratedPhoneNumber());

        Patient patient2 = new Patient();
        patient2.setTimezone("Eastern Time (New York)");
        patient2.setCellPhone(getGeneratedPhoneNumber());

        site.patientSteps().addPatientList(clientName, patientListName);

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFieldsInner(patient)
                .addNewPatientLimitedFieldsInner(patient2)
                .addMultiplePatientsToList(numberOfPatients);

        boolean isPatientAddedToPatientList =
                site.patientSteps().arePatientsAddedToPatientList(numberOfPatients, patient.getCellPhone(), patient2.getCellPhone());

        Assert.assertTrue(isPatientAddedToPatientList, " Patient was not added to list");
    }

    @Test(description = "Delete patient from patient list")
    public void deletePatientFromPatientList_MHM_T155(){
        //Test data
        String programName = "Patient program " + getFormattedDate("dd-MM-yy-H-mm");
        String patientListName = getRandomString();
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setFirstName(getRandomString());

        site.patientSteps().addPatientList(clientName, patientListName);

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFieldsInner(patient)
                .addPatientToList(patient.getFirstName(), patientListName);

        site.patientSteps().deletePatientFromPatientList(patient.getFirstName(), patientListName);

        boolean isPatientRemovedFromUsersPage = site.programSteps().isPatientRemovedFromUsersPage(patient.getFirstName());
        boolean isPatientRemovedFromProgramPage = site.programSteps().isPatientRemovedFromProgramPage(client, programName, patient.getFirstName());

        softAssert.assertTrue(isPatientRemovedFromUsersPage, "Patient was not deleted from Users page");
        softAssert.assertTrue(isPatientRemovedFromProgramPage, "Patient was not deleted from program page");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
