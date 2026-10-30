import org.example.ex15CalculateRecipeScore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Ex15Test {
    @Test
    fun `given the description of the ingredients calculates the optimum score`() {
        val descriptions = """
            Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
            Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
        """.trimIndent()
        val score = ex15CalculateRecipeScore(descriptions)
        assertEquals(57600000, score)
    }

}