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
        assertEquals(4, ex18CalculateLightsOn(initialState, 4, emptyList()))
    }

    @Test
    fun `given x steps and lights initial state with corners always on, after those steps should calculate the number of lights on`() {
        val initialState = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
        """.trimIndent()
        assertEquals(17, ex18CalculateLightsOn(initialState, 5, listOf(0 to 0, 0 to 5, 5 to 0, 5 to 5)))
    }
}