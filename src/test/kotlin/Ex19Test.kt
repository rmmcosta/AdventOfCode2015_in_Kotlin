import org.example.ex19CalculateMolecules
import org.example.ex19StepsToFabricateMolecule
import org.example.readTextFromResource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex19Test {
    @ParameterizedTest(name = "For this description '{0}' there are {1} possible molecules")
    @CsvSource(
        "ex19_test_description1, 4",
        "ex19_test_description2, 7",
    )
    fun `given an initial molecule and the possible replacements calculates the possible molecules`(
        testFile: String,
        possibleMolecules: Int
    ) {
        val description = readTextFromResource(testFile)
        assertEquals(possibleMolecules, ex19CalculateMolecules(description))
    }

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
        assertEquals(steps, ex19StepsToFabricateMolecule(description))
    }
}