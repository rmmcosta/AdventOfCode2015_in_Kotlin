import kotlinx.coroutines.runBlocking
import org.example.calculateMatchZerosHashLowestPositiveNumber
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex4Test {
    @ParameterizedTest(name = "given the input {0} the lowest positive number of the 5 zeros md5 hash is {1}")
    @CsvSource(
        "abcdef, 609043",
        "pqrstuv, 1048970"
    )
    fun `given part of an input should return the second part for five initial zeros`(given: String, expected: Int) {
        val result = runBlocking { calculateMatchZerosHashLowestPositiveNumber(given, "00000") }
        assertEquals(expected, result)
    }

}