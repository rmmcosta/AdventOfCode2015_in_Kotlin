import org.example.ex11GenerateNewPassword
import org.example.ex11IsPasswordValid
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex11Test {
    @ParameterizedTest(name = "For a previous password {0} the new password, {1}")
    @CsvSource(
        "abcdefgh, abcdffaa",
        "ghijklmn, ghjaabcc"
    )
    fun `given the previous password it should generate the new one correctly`(given: String, expected: String) {
        assertEquals(expected, ex11GenerateNewPassword(given))
    }

    @ParameterizedTest(name = "Password {0} should be valid {1}")
    @CsvSource(
        "hijklmmn, false",
        "abbceffg, false",
        "abbcegjk, false",
        "abcdffaa, true",
        "ghjaabcc, true"
    )
    fun `given a password it should be correctly evaluated as valid or invalid`(given: String, expected: Boolean) {
        assertEquals(expected, ex11IsPasswordValid(given))
    }
}