import org.example.ex12SumAllNumbers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex12Test {
    @ParameterizedTest(name = "{0} has a sum of {1}")
    @CsvSource(
        value = [
            "[1,2,3]; 6",
            "{\"a\":2,\"b\":4}; 6",
            "[[[3]]]; 3",
            "{\"a\":{\"b\":4},\"c\":-1}; 3",
            "{\"a\":[-1, 1] }; 0",
            "[-1, { \"a\":1 }]; 0",
            "[]; 0",
            "{}; 0"
        ], delimiter = ';'
    )
    fun `given a valid json document the sum of numbers are well calculated`(given: String, expected: Int) {
        assertEquals(expected, ex12SumAllNumbers(given))
    }

}