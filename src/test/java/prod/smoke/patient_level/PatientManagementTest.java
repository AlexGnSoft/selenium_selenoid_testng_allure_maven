package prod.smoke.patient_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
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
    }

    @Test(description = "Create patient list")
    public void createPatientList_MHM_T150(){
        //Test data
        String patientListName = getRandomString();
        patient.setFirstName("PatientManagementTestPatient-" + getRandomString());

        site.patientSteps().addPatientList(clientName, patientListName);

        boolean isPatientListCreated = site.patientSteps().isPatientListCreated(patientListName);

        Assert.assertTrue(isPatientListCreated, "Patient list was not created");
    }

    @Test(description = "Add patient to a patient list")
    public void addPatientToList_MHM_T151(){
        //Test data
        String programName = getRandomString();
        String patientListName = getRandomString();
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.patientSteps().addPatientList(clientName, patientListName);

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatientLimitedFieldsInner(patient)
                .addPatientToList(patient.getFirstName(), patientListName);

        boolean isPatientAddedToPatientList =
                site.patientSteps().isPatientAddedToPatientList(patientListName, patient.getFirstName());

       // Assert.assertTrue(isPatientAddedToList, " Patient was not added to list");
    }






    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
