import org.example.ex19StepsToFabricateMoleculeWithFiles
import org.example.readTextFromResource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex19Part2WithFilesTest {
    @ParameterizedTest(name = "For this description '{0}' there are {1} possible molecules")
    @CsvSource(
        "ex19_test_description1, 3",
        "ex19_test_description2, 6",
    )
    fun `given the requested molecule and the possible replacements calculates the steps to get the final molecule`(
        testFile: String,
        steps: Int
    ) {
        val description = readTextFromResource(testFile)
        assertEquals(steps, ex19StepsToFabricateMoleculeWithFiles(description))
    }
}