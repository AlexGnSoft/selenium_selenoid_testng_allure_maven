package prod.patient_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import prod.base.BaseTest;

/**
 * Abstract class to be extended by all Patient level tests.
 * Preparations that common for all patient level tests should be implemented here.
 */
public abstract class AbstractPatientLevelTest extends BaseTest {

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
}
