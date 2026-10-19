import org.example.calculatePaperOrder
import org.example.calculateRibbonLength
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex2Test {
    @ParameterizedTest(name = "'{0}' requires {1} square feet of paper")
    @CsvSource(
        "2x3x4, 58",
        "1x1x10, 43",
        "13x5x19, 879",
        "7x7x6, 308"
    )
    fun `given the dimensions of the present must calculate the right paper area needed`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculatePaperOrder(given))
    }

    /*
    A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34 feet.
 A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14 feet. 
     */

    @ParameterizedTest(name = "'{0}' requires {1} feet of ribbon")
    @CsvSource(
        "2x3x4, 34",
        "1x1x10, 14",
        "13x5x19, 1271",
        "7x7x6, 320"
    )
    fun `given the dimensions of the present must calculate the right ribbon length`(
        given: String,
        expected: Int
    ) {
        assertEquals(expected, calculateRibbonLength(given))
    }
}