package prod.smoke.client_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.annotations.BeforeClass;
import prod.base.BaseTest;

import java.util.List;

/**
 * Abstract class to be extended by all Client level tests.
 * Preparations that common for all client level tests should be implemented here.
 */
public abstract class AbstractClientLevelTest extends BaseTest {

    /**
     * For client level test we are going to create separate client for each class
     * to avoid concurrency issues
     */
    /*
    @BeforeSuite(alwaysRun = true)
    public void testDataPreparation() {
        site.loginSteps().openSite().loginAs(user, password);
    }
*/
    @BeforeClass(alwaysRun = true)
    public void testDataPreparation() {
        site.loginSteps().openSite().loginAs(user, password);
    }

    protected Client getTestClientByCode(String clientCode) {
        return getTestClientByCode(clientCode, "twilioSmsSender5 [TWILIO +17542272273]");
    }

    protected Client getTestClientByCode(String clientCode, String endpoint) {
        Client client = site.adminToolsSteps().getClientByCode(clientCode);
        if (client != null) {
            return client;
        }
        Client newClient = new Client();
        newClient.setName(clientCode);
        newClient.setModules(Module.CHECK_ALL);
        newClient.setCode(clientCode);
        newClient.setEndpoint(endpoint);
        site.adminToolsSteps().addNewClient(newClient, Module.CHECK_ALL);
        return newClient;
    }

    public String getTestProgramByName(String programName, Client client) {
        try {
            site.programSteps().goToProgramSettings(client.getName(), programName);
        } catch (Throwable t) {
            //TODO: implement without try catch (to avoid unnecessary memory usage)
            site.programSteps().addNewProgram(client.getName(), programName, ProgramAccess.PUBLIC);
        }
        return programName;
    }

    protected Patient getTestPatientByName(String name, Client client, String programName) {
        List<Patient> patientList = site.programSteps().getPatients(client, programName);
        if (patientList.size() == 0) {
            Patient patient = new Patient();
            patient.setFirstName(name);
            patient.setLastName("Automator");
            patient.setSex(Sex.MALE);
            int mobileNumberRandomized = getRandomNumber(6);
            patient.setCellPhone("+15554" + mobileNumberRandomized);
            patient.setTimezone("Eastern Time (New York)");
            site.programSteps().addNewPatient(patient, client, programName);
            return patient;
        } else {
            return patientList.stream()
                    .filter(patient -> patient.getFirstName().equalsIgnoreCase(name))
                    .findFirst().get();
        }
    }
}
