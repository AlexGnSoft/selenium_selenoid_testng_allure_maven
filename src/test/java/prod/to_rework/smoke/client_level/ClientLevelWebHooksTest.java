package prod.to_rework.smoke.client_level;

import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import prod.smoke.client_level.AbstractClientLevelTest;

//FIXME: due to app issue with webhook ids we are not able to run this test on CI
public class ClientLevelWebHooksTest extends AbstractClientLevelTest {

    private String programName;
    private Client client;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("Webhooks client");
        programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
    }

    @Test(description = "Add a link and webhook name, try adding it to an SMS message to check if the shortened link that goes out leads to a needed link")
    public void addedFooterAppearsOnWebForm() {
        //TODO: remove once webhook issue naming will be resolved
        //String webhookName = "webhookName" + getPersistentCounter("webhook");
        String webhookName = "webhookName" + getRandomString();
        site.clientSteps()
                .addWebhook(client, webhookName, "http://webhooktest.com", null);
        //site.campaignSteps()
                //.addCampaign(client, "AutoTestCampaign", Module.EDUCATION, CampaignAccess.PUBLIC, "Automation created campaign", null);
        //TODO: implement testcase assertion
        System.out.println();
    }
}
