import org.example.isNice
import org.example.isNice2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex5Test {
    @ParameterizedTest(name = "{0} is a nice String {1}")
    @CsvSource(
        "ugknbfddgicrmopn, true",
        "aaa, true",
        "jchzalrnumimnmhp, false",
        "haegwjzuvuyypxyu, false",
        "dvszwmarrgswjxmb, false"
    )
    fun `given a nice string it must return true, otherwise must return false`(given: String, expected: Boolean) {
        assertEquals(expected, isNice(given))
    }

    @ParameterizedTest(name = "{0} is a nice 2 String {1}")
    @CsvSource(
        "qjhvhtzxzqqjkmpb, true",
        "xyxy, true",
        "aaa, false",
        "xxxxyx, true",
        "xckozymymezzarpy, true",
        "xxxx, false",
        "aaayaa, false",
        "aayaaa, false",
        "baaybaya, true",
        "bababaya, true",
        "aabcdefgaa, false",
        "aabcbefgaa, true",
        "abcdefeghi, false",
        "abcdefeghiab, true",
        "xxyxx, true",
        "xxyxa, false",
        "uurcxstgmygtbstg, false",
        "ieodomkazucvgmuy, false",
        "uaurcxstgmygtbstg, true",
        "ieodomkaiezucvgmuy, true",
        "rxexwwwwnu, true"
    )
    fun `given a nice 2 string it must return true, otherwise must return false`(given: String, expected: Boolean) {
        assertEquals(expected, isNice2(given))
    }

}