import org.example.ex20GetHouseNumberByPresentsReceived
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex20Test {
    @ParameterizedTest(name = "for {0} presents house {1} is the winner")
    @CsvSource(
        "10, 1",
        "70, 4",
        "30, 2",
        "60, 5",
        "120, 6",
        "80, 7",
        "150, 8",
        "130, 9"
    )
    fun `given x elfs and y presents, when requesting the house which got the y presents, then it answers correctly`(
        presents: Int,
        house: Int
    ) {
        assertEquals(house, ex20GetHouseNumberByPresentsReceived(presents))
    }

}