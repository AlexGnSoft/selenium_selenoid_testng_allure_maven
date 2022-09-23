package prod.smoke.client_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
        client.setCode("AutoGenerated-" + getFormattedDate("dd-MM-yy-H-mm-ss"));
        client.setName("Automator " + getRandomString());
        client.setEndpoint(twilioSmsSenderEndPoint);
        client.setModules(Module.getAllModules());
    }

    @Test(description = "Add new client with SMS aggregator and all modules")
    public void addNewClient() {
        Client actualClient = site.adminToolsSteps()
                .addNewClient(client, Module.CHECK_ALL)
                .getClientByCode(client.getCode());
        Assert.assertEquals(actualClient, client, "Actual client differs from expected client");
    }

    public void editClientsModules() {
        Module[] modulesToSet = {Module.BIOMETRIC, Module.EDUCATION, Module.MOTIVATION};
        client.setModules(modulesToSet);

        Client updatedClient = site.adminToolsSteps()
                .editClientsModules(client.getCode(), modulesToSet)
                .getClientByCode(client.getCode());

        Assert.assertEquals(updatedClient, client, "Actual client data differs from expected");
    }

    @Test(description = "Check client's modules for messages", dependsOnMethods = "editClientsModules")
    public void checkClientsModulesForMessages() {
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm-ss");
        site.programSteps()
                .addNewProgram(client.getName(), programName, ProgramAccess.PUBLIC);
        List<Module> availableModules = site.messagesSteps()
                .goToMessagesTab()
                .getAvailableModules(client.getName());

        Assert.assertEquals(availableModules, client.getModules(),
                "Modules available for messaging are not equal to users modules.\n" +
                        "Available: " + availableModules + "\n" +
                        "Client modules: " + client.getModules() + "\n");
    }

    @Test(description = "Add additional language for client", dependsOnMethods = "checkClientsModulesForMessages")
    public void addAdditionalLanguageForClient() {
        List<Language> expectedLanguages = Arrays.asList(Language.CH, Language.DU);

        site.loginSteps().openSite().loginAs(user, password);
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
