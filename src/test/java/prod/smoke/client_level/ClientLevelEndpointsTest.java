package prod.smoke.client_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ClientLevelEndpointsTest extends AbstractClientLevelTest {

    List<String> expectedEndpoints = Arrays.asList("twilioSmsSender5 [TWILIO +17542272273]");

    private Patient patient;
    private Client client;
    private String programName;

    @BeforeClass
    public void setUp() {
        client = getTestClientByCode("Autoresponder client");
        programName = getTestProgramByName("ClientEndpoints program", client);
        patient = getTestPatientByName("EnpointsPatient", client, programName);
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
