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
            "[{\"a\":{\"e\":{\"c\":{\"d\":{\"b\":69,\"d\":159,\"f\":2},\"c\":\"green\",\"h\":65,\"b\":{\"c\":126,\"a\":106,\"b\":190,\"d\":-40},\"g\":134,\"f\":\"red\"},\"h\":87,\"b\":[-3,\"yellow\",50,120],\"g\":{\"e\":[138,83,\"red\"],\"c\":[\"yellow\",[\"red\",177,98,\"blue\",179,\"blue\",\"violet\",23],108,[\"green\",17,-46,3,99],-43,46,\"orange\",\"yellow\",{\"a\":192,\"b\":39},57],\"a\":\"red\",\"b\":195,\"d\":172}}}}]; 254",
        ], delimiter = ';'
    )
    fun `given a valid json document the sum of non red numbers are well calculated`(given: String, expected: Int) {
        assertEquals(expected, ex12SumAllNonRedNumbers(given))
    }
}