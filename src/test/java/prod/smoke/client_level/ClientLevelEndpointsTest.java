package prod.smoke.client_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
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
        client = getTestClientByCode("ClientLevelTestEndpoints client");
        programName = getTestProgramByName("ClientLevelTestEndpoints program", client);
        patient = getTestPatientByName("ClientLevelTestEndpointsPatient", client, programName);
    }

    @Test(description = "Check client level endpoints are available on program level")
    public void endpointAvailableOnProgramLevel() {
        List<String> actualEndpoints = site.programSteps()
                .getEndpointsOnProgramLevel(programName);

        Assert.assertTrue(actualEndpoints.containsAll(expectedEndpoints), "Actual endpoints should contain next values:\n" +
                expectedEndpoints + "\n" +
                "but endpoints is " + actualEndpoints + "\n");
    }

    @Test(description = "Check client level endpoints are available on patient level")
    public void endpointAvailableOnPatientLevel() {
        List<String> actualEndpoints = site.programSteps()
                .getEndpointsOnPatientLevel(client, programName, patient);

        Assert.assertTrue(actualEndpoints.containsAll(expectedEndpoints), "Actual endpoints should contain next values:\n" +
                expectedEndpoints + "\n" +
                "but endpoints is " + actualEndpoints + "\n");
    }
}
