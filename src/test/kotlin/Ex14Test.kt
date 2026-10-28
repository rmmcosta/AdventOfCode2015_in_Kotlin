import org.example.ex14CalculateFastestReindeerDistanceTraveled
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Ex14Test {
    @Test
    fun `given 2 Reindeer when running for 1000 seconds then the correct max distance is calculated correctly`() {
        val reindeerDescriptions = """
            Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
            Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent()

        val maxDistance = ex14CalculateFastestReindeerDistanceTraveled(reindeerDescriptions, 1000)
        assertEquals(1120, maxDistance)
    }

}