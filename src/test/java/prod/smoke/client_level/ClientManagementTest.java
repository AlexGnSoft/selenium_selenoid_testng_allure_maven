package prod.smoke.client_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.*;
import com.carespeak.domain.entities.staff.StaffManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
    private StaffManager staffManager;
    private String clientName;
    private String clientCode;

    @BeforeClass
    public void prepareClientData() {
        String twilioSmsSenderEndPoint = PropertyFileReader.getVariableValue("twilioSmsSender");

        client = new Client();
        client.setCode("AutoGenerated-" + getFormattedDate("dd-MM-yy-H-mm-ss"));
        client.setName("Automator " + getRandomString());
        client.setEndpoint(twilioSmsSenderEndPoint);
        client.setModules(Module.getAllModules());
        clientName = client.getName();
        clientCode = client.getCode();
    }

    @Test(description = "Add new client with all modules")
    public void addNewClient_MHM_T10() {
        Client actualClient = site.adminToolsSteps()
                .addNewClient(client, Module.CHECK_ALL)
                .getClientByCode(clientCode);
        Assert.assertEquals(client, actualClient, "Actual client differs from expected client");
    }

    @Test(description = "Edit clients modules and verify it on Messages level", dependsOnMethods = "addNewClient_MHM_T10")
    public void editClientsModules_MHM_T11() {
        Module[] modulesToSet = {Module.BIOMETRIC, Module.EDUCATION, Module.MOTIVATION, Module.SURVEY};
        client.setModules(modulesToSet);

        Client actualClient = site.adminToolsSteps()
                .getClientByCode(clientCode);

        Client updatedClient = site.adminToolsSteps()
                .editClientsModules(actualClient.getCode(), modulesToSet)
                .getClientByCode(actualClient.getCode());

        Assert.assertTrue(site.messagesSteps().isMessageModulesEqualToClient(modulesToSet, updatedClient), "Client list of modules was not updated");
    }

    @Test(description = "Check client's modules for messages", dependsOnMethods = {"addNewClient_MHM_T10", "editClientsModules_MHM_T11"})
    public void checkClientsModulesForMessages_MHM_T161() {
        List<Module> availableModules = site.messagesSteps()
                .goToMessagesTab()
                .getAvailableModules(clientName);

        Assert.assertEquals(availableModules, client.getModules(),
                "Modules available for messaging are not equal to users modules.\n" +
                        "Available: " + availableModules + "\n" +
                        "Client modules: " + client.getModules() + "\n");
    }

    @Test(description = "Check client's modules for campaigns", dependsOnMethods = {"addNewClient_MHM_T10", "editClientsModules_MHM_T11"})
    public void checkClientsModulesForCampaigns_MHM_T160() {
        List<Module> availableModules = site.campaignSteps()
                .goToCampaignsTab()
                .getAvailableModules(clientName);

        Assert.assertEquals(availableModules, client.getModules(),
                "Modules available for campaigns are not equal to users modules.\n" +
                        "Available: " + availableModules + "\n" +
                        "Client modules: " + client.getModules() + "\n");
    }


    @Test(description = "Add additional language for client", dependsOnMethods = "addNewClient_MHM_T10")
        public void addAdditionalLanguageForClient_MHM_T12() {
        List<Language> expectedLanguages = Arrays.asList(Language.CH, Language.DU);

        List<Language> actualLanguages = site.adminToolsSteps()
                .goToClientSettings(clientCode)
                .addAdditionalLanguage(expectedLanguages)
                .goToClientSettings(clientCode)
                .getAdditionalLanguages();

        Assert.assertTrue(actualLanguages.containsAll(expectedLanguages),
                "Additional languages should contain next values:\n" +
                        Arrays.toString(expectedLanguages.toArray()) + "\n" +
                        "but additional languages is " + Arrays.toString(actualLanguages.toArray()) + "\n");
    }

    @Test(description = "Remove additional language for client", dependsOnMethods = {"addNewClient_MHM_T10", "addAdditionalLanguageForClient_MHM_T12"})
    public void removeAdditionalLanguageForClient_MHM_T162() {
        Language languageToRemove = Language.CH;

        List<Language> actualLanguages = site.adminToolsSteps()
                .goToClientSettings(clientCode)
                .removeAdditionalLanguage(languageToRemove)
                .goToClientSettings(clientCode)
                .getAdditionalLanguages();

        Assert.assertFalse(actualLanguages.contains(languageToRemove),
                "Additional languages should not contain value:\n" +
                        languageToRemove + "\n" +
                        "but additional languages is " + Arrays.toString(actualLanguages.toArray()) + "\n");
    }

    @Test(description = "Create client level program staff manager - Multi client program manager")
    public void createClientLevelProgramStaffManager_MHM_T45() {
        //Test data
        staffManager = new StaffManager();
        staffManager.setFirstName(getRandomString());
        staffManager.setLastName(getRandomString());
        staffManager.setEmail(getRandomString() + "@gmail.com");
        staffManager.setTimezone("Eastern Time (New York)");
        String MULTI_CLIENT_ADMIN = "Multi client program manager";
        String ROLE_PROGRAM_ADMIN = "Client level program manager";
        String ROLE_PROGRAM_STAFF = "Regular staff program manager";
        String ROLE_AGGREGATE_ONLY_STAFF = "Staff dashboard read-only";

        client = getTestClientByCode(clientCode);
        site.adminToolsSteps().addStaffManager(staffManager, MULTI_CLIENT_ADMIN, staffManager.getTimezone());

        boolean isStaffManagerCreated =
                site.adminToolsSteps().isStaffManagerCreated(client.getName(), staffManager.getEmail());

        Assert.assertTrue(isStaffManagerCreated, "Staff manager was not created");
    }

    @Test(description = "Delete created client level program staff manager - Client level program manager")
    public void deleteClientLevelProgramStaffManager_MHM_T53() {
        //Test data
        staffManager = new StaffManager();
        staffManager.setFirstName(getRandomString());
        staffManager.setLastName(getRandomString());
        staffManager.setEmail(getRandomString() + "@gmail.com");
        staffManager.setTimezone("Eastern Time (New York)");
        String MULTI_CLIENT_ADMIN = "Multi client program manager";
        String ROLE_PROGRAM_ADMIN = "Client level program manager";
        String ROLE_PROGRAM_STAFF = "Regular staff program manager";
        String ROLE_AGGREGATE_ONLY_STAFF = "Staff dashboard read-only";

        client = getTestClientByCode(clientCode);
        site.adminToolsSteps()
                .addStaffManager(staffManager, ROLE_PROGRAM_ADMIN, staffManager.getTimezone())
                .deleteStaffManager(staffManager);

        boolean isStaffManagerDeleted = site.adminToolsSteps().isStaffManagerDeleted(staffManager);

        Assert.assertTrue(isStaffManagerDeleted, "Staff manager was not deleted");
    }

    @Test(description = "Impersonate a Staff member - Client level program manager")
    public void impersonateStaffMember_MHM_T55() {
        //Test data
        staffManager = new StaffManager();
        staffManager.setFirstName(getRandomString());
        staffManager.setLastName(getRandomString());
        staffManager.setEmail(getRandomString() + "@gmail.com");
        staffManager.setTimezone("Eastern Time (New York)");
        String MULTI_CLIENT_ADMIN = "Multi client program manager";
        String ROLE_PROGRAM_ADMIN = "Client level program manager";
        String ROLE_PROGRAM_STAFF = "Regular staff program manager";
        String ROLE_AGGREGATE_ONLY_STAFF = "Staff dashboard read-only";

        client = getTestClientByCode(clientCode);
        site.adminToolsSteps()
                .addStaffManager(staffManager, ROLE_PROGRAM_ADMIN, staffManager.getTimezone())
                .impersonateStaffMember(staffManager);

        boolean isStaffManagerImpersonated = site.adminToolsSteps().isStaffManagerImpersonated(staffManager);

        Assert.assertTrue(isStaffManagerImpersonated, "Staff manager was not impersonated");
    }




    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);

//        Client shouldBeRemoved = site.adminToolsSteps()
//                .getClientByCode(client.getCode());
//        Assert.assertNull(shouldBeRemoved, "Client " + client + " was not removed!");
    }
}
