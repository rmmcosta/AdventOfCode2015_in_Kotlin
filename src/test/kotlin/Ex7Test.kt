import org.example.calculateSignalOnWire
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex7Test {
    @ParameterizedTest(name = "wire {0} should have the signal {1}")
    @CsvSource(
        "d, 72",
        "e, 507",
        "f, 492",
        "g, 114",
        "h, 65412",
        "i, 65079",
        "x, 123",
        "y, 456"
    )
    fun `given a set of instructions, the given wire must have the correct signal`(wire: String, expectedSignal: Int) {
        val instructions = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent()
        assertEquals(expectedSignal, calculateSignalOnWire(instructions, wire))
    }

}