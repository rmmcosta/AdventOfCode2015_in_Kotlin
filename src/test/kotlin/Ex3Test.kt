import org.example.calculateHousesWithPresents
import org.example.calculateHousesWithPresentsWithRobotSanta
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex3Test {
    @ParameterizedTest(name = "{0} leads to {1} with presents")
    @CsvSource(
        ">, 2",
        "^>v<, 4",
        "^v^v^v^v^v, 2"
    )
    fun `given the directions should correctly calculate the houses with at least one present`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculateHousesWithPresents(given))
    }


    @ParameterizedTest(name = "{0} leads to {1} with presents")
    @CsvSource(
        "^v, 3",
        "^>v<, 3",
        "^v^v^v^v^v, 11"
    )
    fun `given the directions for Santa and RobotSanta, should correctly calculate the houses with at least one present`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculateHousesWithPresentsWithRobotSanta(given))
    }
}