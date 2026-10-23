import org.example.ex10CalculateLookAndSay
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex10Test {
    @ParameterizedTest(name = "The sequence {0} should look and say like this {1}")
    @CsvSource(
        "1, 11",
        "11, 21",
        "21, 1211",
        "1211, 111221",
        "111221, 312211"
    )
    fun `the sequence should be correctly looked and said`(given: String, expected: String) {
        assertEquals(expected, ex10CalculateLookAndSay(given))
    }
}