import org.example.ex18CalculateLightsOn
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Ex18Test {
    @Test
    fun `given x steps and lights initial state, after those steps should calculate the number of lights on`() {
        val initialState = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
        """.trimIndent()
        assertEquals(4, ex18CalculateLightsOn(initialState, 4))
    }

}