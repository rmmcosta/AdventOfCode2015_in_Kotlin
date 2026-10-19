import org.example.calculateFirstBasementPosition
import org.example.calculateFloor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/*
For example:

(()) and ()() both result in floor 0.
((( and (()(()( both result in floor 3.
))((((( also results in floor 3.
()) and ))( both result in floor -1 (the first basement level).
))) and )())()) both result in floor -3.
To what floor do the instructions take Santa?
 */
class Ex1Test {
    @ParameterizedTest(name = "'{0}' result in floor {1}")
    @CsvSource(
        "(()), 0",
        "()(), 0",
        "(((, 3",
        "(()(()(, 3",
        "))(((((, 3",
        "()),-1",
        "))(,-1",
        "))),-3",
        ")())()),-3"
    )
    fun `given the instructions, when moving according to it, should end in the expected floor`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculateFloor(given))
    }

    @ParameterizedTest(name = "'{0}' first position to enter the basement is {1}")
    @CsvSource(
        "), 1",
        "()()), 5"
    )
    fun `given the instructions, when moving according to it, should enter the basement in the expected position`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculateFirstBasementPosition(given))
    }
}