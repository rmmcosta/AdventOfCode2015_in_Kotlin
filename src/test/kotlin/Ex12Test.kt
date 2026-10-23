import org.example.ex12SumAllNumbers
import org.example.ex12SumAllNonRedNumbers
import org.junit.jupiter.api.Assertions.*
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

    @ParameterizedTest(name = "{0} has a sum of {1}")
    @CsvSource(
        value = [
            "[1,2,3]; 6",
            "[1,{\"c\":\"red\",\"b\":2},3]; 4",
            "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}; 0",
            "[1,\"red\",5]; 6"
        ], delimiter = ';'
    )
    fun `given a valid json document the sum of non red numbers are well calculated`(given: String, expected: Int) {
        assertEquals(expected, ex12SumAllNonRedNumbers(given))
    }
}