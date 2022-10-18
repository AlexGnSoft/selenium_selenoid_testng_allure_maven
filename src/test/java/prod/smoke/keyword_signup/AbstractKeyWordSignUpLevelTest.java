package prod.smoke.keyword_signup;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import org.testng.Assert;
import prod.base.BaseTest;

public abstract class AbstractKeyWordSignUpLevelTest extends BaseTest {
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

    protected void removeClient(Client client) {
        if (client != null) {
            site.adminToolsSteps()
                    .removeClient(client);

            site.loginSteps()
                    .openSite()
                    .loginAs(user, password);
            Client shouldBeRemoved = site.adminToolsSteps()
                    .getClientByCode(client.getCode());
            Assert.assertNull(shouldBeRemoved, "Client " + client + " was not removed!");
        }
    }
}
