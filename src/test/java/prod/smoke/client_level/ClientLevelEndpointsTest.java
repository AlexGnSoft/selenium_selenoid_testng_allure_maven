package prod.smoke.client_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.patient.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class ClientLevelEndpointsTest extends AbstractClientLevelTest {

    private List<String> expectedEndpoints = Arrays.asList(PropertyFileReader.getVariableValue("twilioSmsSender"));

    private Patient patient;
    private Client client;
    private String programName;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ClientLevelTestEndpoints client " + getFormattedDate("dd-MM-yy-H-mm-ss"));
        programName = getTestProgramByName("ClientLevelTestEndpoints program " + getFormattedDate("dd-MM-yy-H-mm-ss"), client);
        patient = getTestPatientByName("ClientLevelTestEndpointsPatient", client, programName);
    }

    @Test(description = "Check client level endpoints are available on program level")
    public void endpointAvailableOnProgramLevel_MHM_T167() {

        List<String> actualEndpoints = site.programSteps()
                .getEndpointsOnProgramLevel(programName);

        Assert.assertTrue(actualEndpoints.containsAll(expectedEndpoints), "Actual endpoints should contain next values:\n" +
                expectedEndpoints + "\n" +
                "but endpoints is " + actualEndpoints + "\n");
    }

    @Test(description = "Check client level endpoints are available on patient level")
    public void endpointAvailableOnPatientLevel_MHM_T169() {
        List<String> actualEndpoints = site.programSteps()
                .getEndpointsOnPatientLevel(client, programName, patient);

        Assert.assertTrue(actualEndpoints.containsAll(expectedEndpoints), "Actual endpoints should contain next values:\n" +
                expectedEndpoints + "\n" +
                "but endpoints is " + actualEndpoints + "\n");
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpClientData() {
//        site.programSteps().removeProgram(client, programName);
//        List<String> programs = site.programSteps().getProgramsForClient(client);
//        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
        site.adminToolsSteps()
                .removeClient(client);
    }
}