package prod.smoke.client_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.steps.imp.prod.ProdMessagesSteps;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Basic client management actions tests, for each test separate client will be used
 * <p>
 * 1. Add a new client and SMS Aggregator and all modules
 * 2. Edit client modules
 * 3. Check client modules for messages
 * 4. Add default and additional languages
 * 5. Remove additional language
 * 6. Remove client
 */
public class ClientManagementTest extends AbstractClientLevelTest {

    private Client client;

    @BeforeClass
    public void prepareClientData() {
        String twilioSmsSenderEndPoint = PropertyFileReader.getVariableValue("twilioSmsSender");

        client = new Client();
        client.setCode("AutoGenerated1-" + getFormattedDate("dd-MM-yy-H-mm-ss"));
        client.setName("Automator " + getRandomString());
        client.setEndpoint(twilioSmsSenderEndPoint);
        client.setModules(Module.getAllModules());
    }

    @Test(description = "Add new client with SMS aggregator and all modules")
    public void addNewClient() {
        Client actualClient = site.adminToolsSteps()
                .addNewClient(client, Module.CHECK_ALL)
                .getClientByCode(client.getCode());
        Assert.assertEquals(client, actualClient, "Actual client differs from expected client");
    }

    @Test(description = "Edit clients modules")
    public void editClientsModules() {
        Module[] modulesToSet = {Module.BIOMETRIC, Module.EDUCATION, Module.MOTIVATION};
        client.setModules(modulesToSet);

        Client actualClient = site.adminToolsSteps()
                .addNewClient(client, Module.CHECK_ALL)
                .getClientByCode(client.getCode());

        Client updatedClient = site.adminToolsSteps()
                .editClientsModules(actualClient.getCode(), modulesToSet)
                .getClientByCode(actualClient.getCode());

        Assert.assertTrue(site.messagesSteps().isMessageModulesEqualToClient(modulesToSet, updatedClient), "Client list of modules was not updated");
    }

    @Test(description = "Check client's modules for messages", dependsOnMethods = "editClientsModules")
    public void checkClientsModulesForMessages() {
        List<Module> availableModules = site.messagesSteps()
                .goToMessagesTab()
                .getAvailableModules(client.getName());

        Assert.assertEquals(availableModules, client.getModules(),
                "Modules available for messaging are not equal to users modules.\n" +
                        "Available: " + availableModules + "\n" +
                        "Client modules: " + client.getModules() + "\n");
    }

    @Test(description = "Check client's modules for campaigns", dependsOnMethods = "editClientsModules")
    public void checkClientsModulesForCampaigns() {
        List<Module> availableModules = site.campaignSteps()
                .goToCampaignsTab()
                .getAvailableModules(client.getName());

        Assert.assertEquals(availableModules, client.getModules(),
                "Modules available for campaigns are not equal to users modules.\n" +
                        "Available: " + availableModules + "\n" +
                        "Client modules: " + client.getModules() + "\n");
    }


    @Test(description = "Add additional language for client", dependsOnMethods = "addNewClient")
    public void addAdditionalLanguageForClient() {
        List<Language> expectedLanguages = Arrays.asList(Language.CH, Language.DU);

        List<Language> actualLanguages = site.adminToolsSteps()
                .goToClientSettings(client.getCode())
                .addAdditionalLanguage(expectedLanguages)
                .goToClientSettings(client.getCode())
                .getAdditionalLanguages();

        Assert.assertTrue(actualLanguages.containsAll(expectedLanguages),
                "Additional languages should contain next values:\n" +
                        Arrays.toString(expectedLanguages.toArray()) + "\n" +
                        "but additional languages is " + Arrays.toString(actualLanguages.toArray()) + "\n");
    }

    @Test(description = "Remove additional language for client", dependsOnMethods = "addAdditionalLanguageForClient")
    public void removeAdditionalLanguageForClient() {
        Language languageToRemove = Language.CH;

        List<Language> actualLanguages = site.adminToolsSteps()
                .goToClientSettings(client.getCode())
                .removeAdditionalLanguage(languageToRemove)
                .goToClientSettings(client.getCode())
                .getAdditionalLanguages();

        Assert.assertFalse(actualLanguages.contains(languageToRemove),
                "Additional languages should not contain value:\n" +
                        languageToRemove + "\n" +
                        "but additional languages is " + Arrays.toString(actualLanguages.toArray()) + "\n");
    }

    @Test(description = "Remove created client test", dependsOnMethods = "removeAdditionalLanguageForClient")
    public void removeClient() {
        site.loginSteps()
                .openSite()
                .loginAs(user, password);
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
