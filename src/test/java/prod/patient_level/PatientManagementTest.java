package prod.patient_level;

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

    @Test(description = "Create patient list")
    public void createPatientList_MHM_T150(){




    }




    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
