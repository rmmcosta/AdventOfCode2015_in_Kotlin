package org.example

import java.io.File

/*
which strings in his text file are naughty or nice
A nice string is one with all of the following properties:
It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
For example:

ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
jchzalrnumimnmhp is naughty because it has no double letter.
haegwjzuvuyypxyu is naughty because it contains the string xy.
dvszwmarrgswjxmb is naughty because it contains only one vowel.
How many strings are nice?
 */

fun calculateNiceStrings(text: String): Int = text.split("\n").count(::isNice)

fun calculateNice2Strings(text: String): Int {
    File("ex5_goodStrings").writeText("")
    File("ex5_badStrings").writeText("")
    return text.split("\n").count(::isNice2)
}

fun isNice(text: String): Boolean =
    calculateVowels(text) >= 3 && hasLetterTwiceInARow(text) && !hasBadStrings(text)

private fun hasBadStrings(text: String): Boolean {
    val badStringsRegex = Regex("(ab)|(cd)|(pq)|(xy)")
    return text.contains(badStringsRegex)
}

private fun hasLetterTwiceInARow(text: String): Boolean {
    var previous: Char? = null
    for (c in text) {
        if (c == previous) {
            return true
        }
        previous = c
    }
    return false
}

private fun calculateVowels(text: String): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u')
    return text.count { it in vowels }
}

/*
Now, a nice string is one with all of the following properties:

It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), 
but not like aaa (aa, but it overlaps).
It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
For example:

qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
 */

fun isNice2(text: String): Boolean {
    val isNice = hasValidTwoLettersPairsAppearingTwice(text) && hasOneLetterRepeatingWithOneBetween(text)
    if (isNice) {
        File("ex5_goodStrings").appendText("$text\n")
    } else {
        File("ex5_badStrings").appendText("$text\n")
    }
    return isNice
}

fun hasOneLetterRepeatingWithOneBetween(text: String): Boolean {
    var previousPrevious: Char? = null
    var previous: Char? = null
    for (c in text) {
        if (c == previousPrevious && c != previous) {
            return true
        }
        previousPrevious = previous
        previous = c
    }
    return false
}

fun hasValidTwoLettersPairsAppearingTwice(text: String): Boolean {
    val pairs = buildPairs(text)
    if (hasTwoEqualConsecutivePairs(pairs))
        return false
    val countPairs = mutableMapOf<String, Int>()
    for ((first, second) in pairs) {
        val pairKey = "$first$second"
        countPairs[pairKey] = countPairs.getOrDefault(pairKey, 0) + 1
    }
    //println("countPairs: $countPairs")
    return countPairs.values.any { it > 1 }
}

private fun buildPairs(text: String): List<Pair<Char, Char>> =
    text.substring(0, text.length - 1).toCharArray().zip(text.substring(1, text.length).toCharArray())

fun hasTwoEqualConsecutivePairs(pairs: List<Pair<Char, Char>>): Boolean {
    val consecutivePairs = pairs.subList(0, pairs.size - 2).zip(pairs.subList(2, pairs.size))
    //if we have 2 pairs completely equal then we need to remove all the rows involving that pair
    //and only then we can check for the overlap
    val completelyEqualPair = consecutivePairs.filter { (pair1, pair2) ->
        pair1.first == pair1.second && pair1.second == pair2.first && pair2.first == pair2.second
    }.map { (pair1, _) -> pair1 }

    val updatedConsecutivePairs = consecutivePairs.filterNot { (pair1, pair2) ->
        completelyEqualPair.contains(pair1) || completelyEqualPair.contains(pair2)
    }
    return updatedConsecutivePairs.any { (pair1, pair2) ->
        (pair1.first == pair1.second && pair1.second == pair2.first)
                || (pair1.second == pair2.first && pair2.first == pair2.second)
    }
}
