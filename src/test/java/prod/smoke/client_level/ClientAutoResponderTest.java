package prod.smoke.client_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Client Level Autoresponder tests
 * <p>
 * 1. Add autoresponder and try weekend/weekdays combination and check autoresponse
 */
public class ClientAutoResponderTest extends AbstractClientLevelTest {

    private Patient patient;
    private Client client;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ClientLevelTestAutoresponder client " + getFormattedDate("dd-MM-yy-H-mm-ss"));

        patient = new Patient();
        patient.setFirstName("Patient");
        patient.setCellPhone("+15554" + getRandomNumber(6));
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add Auto Responders and check auto response")
    public void addAutoResponders_MHM_T14() {
        String workingDayMessage = "It's working day!";
        String weekendMessage = "It's WEEKEND, yay!!!";
        String programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm-ss");

        String expectedMessage = today().isWeekend() ? weekendMessage : workingDayMessage;

        site.clientSteps()
                .addAutoResponder(client, workingDayMessage, true, Day.getWorkingDays())
                .addAutoResponder(client, weekendMessage, true, Day.getWeekEnds());
        site.programSteps()
                .addNewProgram(client.getName(), programName, ProgramAccess.PUBLIC)
                .rejectUnsolicitedMessages(client, programName, "Accepted|AGREE");

        MessageLogItem actualSms = site.programSteps()
                .addNewPatient(patient, client, programName)
                .simulateResponse(patient.getFirstName(), "AGREE")
                .simulateResponse(patient.getFirstName(), "Accepted")
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(patient.getCellPhone());

        Assert.assertEquals(actualSms.getMessage(), expectedMessage, "Received message does not equal to expected");
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpClientData() {
//        site.programSteps().removeProgram(client, programName);
//        List<String> programs = site.programSteps().getProgramsForClient(client);
//        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
        removeClient(client);
    }
}
