package prod.smoke.campaign_level;

import com.carespeak.domain.entities.client.Client;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CampaignManagementTest extends AbstractCampaignLevelTest {

    private Client client;
    private String clientName;
    private String messageName;


    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("CampaignLevel client " + getRandomString());
        clientName = client.getName();
        messageName = getRandomString();
        getEmailTemplate(clientName);
        getTestEducationalEmailMessage(messageName);
    }

    @Test(description = "Create campaign - Module Educational")
    public void createEducationalCampaign_MHM_T83() {


    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
