package smoke;

import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoResponderTest extends SmokeBaseTest {

    private Patient patient;

    @BeforeClass
    public void prepareClientData() {
        programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        client = createNewClient();
        site.loginSteps().openSite().loginAs(user, password);
        patient = new Patient();
        patient.setFirstName("Patient");
        patient.setLastName("Automator");
        patient.setSex(Sex.MALE);
        patient.setCellPhone("+15554622669");
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add Auto Responders and check auto response")
    public void addAutoResponder() {
        String workingDayMessage = "It's working day!";
        String weekendMessage = "It's WEEKEND, yay!!!";

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

    @AfterClass
    public void removeClient() {
        removeClient(client);
    }
}
