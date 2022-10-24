package prod.smoke.program_level;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramManagementTest extends AbstractProgramLevelTest {

    private Client client;
    private String programName;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestAutoresponder client");
        programName = "AutoGenerated program " + getFormattedDate("dd-MM-yy-H-mm");
    }

    @Test(description = "Add new program")
    public void addNewProgram_MHM_T25() {
        site.programSteps().addNewProgram(client.getName(), programName, ProgramAccess.PUBLIC);

        String actualProgramName = site.programSteps()
                .getProgramByName(client.getName(), programName);
        Assert.assertEquals(actualProgramName, programName, "Actual program differs from expected program");
    }

    @Test(description = "Check that program is set to public", dependsOnMethods = "addNewProgram_MHM_T25")
    public void publicProgram_MHM_T168() {
        String actualAccess = site.programSteps()
                .getProgramAccessModifier(client.getName(), ProgramAccess.PUBLIC.getValue() + " (Visit here)");

        Assert.assertEquals(actualAccess, ProgramAccess.PUBLIC.getValue(), "Actual access differs from expected access");
    }

    @AfterClass(alwaysRun=true)
    public void removeProgram() {
//        site.programSteps().removeProgram(client, programName);
//        List<String> programs = site.programSteps().getProgramsForClient(client);
//        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
        site.adminToolsSteps().removeClient(client);
    }
}
