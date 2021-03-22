package prod.smoke.program_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.AutoRespondersStatus;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramAutoResponder extends AbstractProgramLevelTest {

    private static final String AUTO_RESPONDER_MESSAGE = "Your message cannot be processed. Please call 911 if this is an emergency.";

    private Patient patient;
    private String programName;
    private Client client;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestAutoresponder client");
        programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
        patient = new Patient();
        patient.setFirstName("ProgramAutoresponderPatient");
        patient.setLastName("Automator");
        patient.setSex(Sex.MALE);
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Check auto response on accepted message regexes")
    public void addAutoResponder() {
        site.programSteps()
                .addNewProgram(client.getName(), programName, ProgramAccess.PUBLIC)
                .addNewPatient(patient, client, programName)
                .rejectUnsolicitedMessages(client, programName, "Accepted|AGREE");
        site.programSteps()
                .addAutoResponder(client, programName, AutoRespondersStatus.OVERRIDDEN, AUTO_RESPONDER_MESSAGE);
        site.programSteps()
                .simulateResponse(patient.getFirstName(), "AGREE")
                .simulateResponse(patient.getFirstName(), "Accepted");

        MessageLogItem actualSms = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualSms.getMessage(), AUTO_RESPONDER_MESSAGE, "Received message is not the same as expected!");
    }

    @Test(description = "Check the auto responder goes out as expected when send an unrecognized message")
    public void unrecognizedMessage() {
        site.programSteps()
                .simulateResponse(patient.getFirstName(), "Unrecognized");

        MessageLogItem actualSms = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualSms.getMessage(), AUTO_RESPONDER_MESSAGE, "Received message is not the same as expected!");
    }

    @AfterClass(alwaysRun = true)
    public void removeProgram() {
        site.programSteps().removeProgram(client, programName);
        List<String> programs = site.programSteps().getProgramsForClient(client);
        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
    }
}
