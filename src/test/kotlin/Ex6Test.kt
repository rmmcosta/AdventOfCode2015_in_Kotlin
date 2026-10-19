import org.example.calculateLightsOn
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex6Test {
    @Test
    fun `given an instruction to turn on all the lights then we must have 1_000_000 lights on`() {
        val expected = 1000 * 1000
        assertEquals(expected, calculateLightsOn("turn on 0,0 through 999,999"))
    }

    @Test
    fun `given the first line of lights off, turning them on results in 1000 lights on`() {
        val expected = 1000
        assertEquals(expected, calculateLightsOn("toggle 0,0 through 999,0"))
    }

    @Test
    fun `given the first line of lights on and the rest off, turning them off results in 0 lights on`() {
        val expected = 0
        assertEquals(expected, calculateLightsOn("toggle 0,0 through 999,0\ntoggle 0,0 through 999,0"))
    }

    @Test
    fun `given all lights are on, turn off 499,499 through 500,500 would turn off the middle four lights resulting in 999_996 lights on`() {
        val expected = 1000 * 1000 - 4
        assertEquals(expected, calculateLightsOn("turn on 0,0 through 999,999\nturn off 499,499 through 500,500"))
    }
}