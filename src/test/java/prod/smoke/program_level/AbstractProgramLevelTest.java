package prod.smoke.program_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.ProgramAccess;
import prod.base.BaseTest;

/**
 * Abstract class to be extended by all Program level tests.
 * Preparations that common for all program level tests should be implemented here.
 */
public abstract class AbstractProgramLevelTest extends BaseTest {

    protected Client getTestClientByCode(String clientCode) {
        return getTestClientByCode(clientCode, PropertyFileReader.getVariableValue("twilioSmsSender"));
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
}
