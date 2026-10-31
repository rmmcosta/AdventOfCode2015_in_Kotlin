import org.example.ex17CalculateContainersCombinations
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Ex17Test {
    @Test
    fun `given a list of containers and the qty of eggnog then we calculate the correct combinations`() {
        val containers = """
           20
           15
           10
           5
           5
        """.trimIndent()
        val possibleCombinations = ex17CalculateContainersCombinations(containers, 25)
        assertEquals(4, possibleCombinations)
    }

}