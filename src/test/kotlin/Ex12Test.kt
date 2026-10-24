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
            "[1,\"red\",5]; 6",
            "[[1,2,3],[1,{\"c\":\"red\",\"b\":2},3],{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5},[1,\"red\",5],{\"d\":\"red\",\"e\":{\"c\":\"red\",\"b\":2},\"f\":5},[null,\"green\",[\"yellow\",-11,158,11,{\"e\":\"green\",\"c\":\"red\",\"a\":\"red\",\"b\":{\"a\":168},\"d\":7},\"orange\",107,\"green\",\"red\"]],{\"a\":3}]; 284",
            "[{\"a\":{\"e\":{\"e\":161,\"a\":\"blue\",\"d\":{\"e\":-14,\"a\":\"red\",\"d\":{\"c\":\"yellow\",\"a\":[-35,0],\"b\":\"orange\",\"d\":{\"e\":70,\"a\":\"green\",\"d\":\"blue\",\"j\":12,\"c\":69,\"h\":\"orange\",\"b\":92,\"g\":\"yellow\",\"f\":\"green\",\"i\":121}},\"c\":\"blue\",\"h\":14,\"b\":46,\"g\":62,\"f\":[179]},\"j\":{\"e\":133,\"c\":\"violet\",\"a\":\"orange\",\"b\":\"blue\",\"d\":\"violet\"},\"c\":{\"e\":151,\"a\":\"violet\",\"d\":{\"e\":\"red\",\"c\":93,\"a\":135,\"g\":{\"e\":43,\"c\":\"green\",\"a\":\"orange\",\"b\":\"green\",\"d\":54}}}}}}]; 445",
            "[{\"a\":{\"e\":{\"c\":{\"d\":{\"b\":69,\"d\":159,\"f\":2},\"c\":\"green\",\"h\":65,\"b\":{\"c\":126,\"a\":106,\"b\":190,\"d\":-40},\"g\":134,\"f\":\"red\"},\"h\":87,\"b\":[-3,\"yellow\",50,120],\"g\":{\"e\":[138,83,\"red\"],\"c\":[\"yellow\",[\"red\",177,98,\"blue\",179,\"blue\",\"violet\",23],108,[\"green\",17,-46,3,99],-43,46,\"orange\",\"yellow\",{\"a\":192,\"b\":39},57],\"a\":\"red\",\"b\":195,\"d\":172}}}}]; 254",
        ], delimiter = ';'
    )
    fun `given a valid json document the sum of non red numbers are well calculated`(given: String, expected: Int) {
        assertEquals(expected, ex12SumAllNonRedNumbers(given))
    }
}