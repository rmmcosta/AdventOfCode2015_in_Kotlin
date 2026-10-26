import org.example.ex13CalculateTotalChangeInHappiness
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Constants
import org.junit.jupiter.api.Test

class Ex13Test {
    @Test
    fun `given the potential happiness when we find the optimal seat arrangement then the total change in happiness is correctly calculated`() {
        val potentialHappiness = """
           Alice would gain 54 happiness units by sitting next to Bob.
           Alice would lose 79 happiness units by sitting next to Carol.
           Alice would lose 2 happiness units by sitting next to David.
           Bob would gain 83 happiness units by sitting next to Alice.
           Bob would lose 7 happiness units by sitting next to Carol.
           Bob would lose 63 happiness units by sitting next to David.
           Carol would lose 62 happiness units by sitting next to Alice.
           Carol would gain 60 happiness units by sitting next to Bob.
           Carol would gain 55 happiness units by sitting next to David.
           David would gain 46 happiness units by sitting next to Alice.
           David would lose 7 happiness units by sitting next to Bob.
           David would gain 41 happiness units by sitting next to Carol. 
        """.trimIndent()

        assertEquals(330, ex13CalculateTotalChangeInHappiness(potentialHappiness))
    }

}