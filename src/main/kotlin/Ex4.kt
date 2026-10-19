package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.apache.commons.codec.digest.DigestUtils
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/*
find MD5 hashes which, in hexadecimal, start with at least five zeroes.
The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal
find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash
For example:
If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), 
and it is the lowest such number to do so.
If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; 
that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
 */
suspend fun calculateMatchZerosHashLowestPositiveNumber(input: String, match: String): Int {
    val max = 10_000_000
    val pivot = 100_000
    val possibleResults = coroutineScope {
        (0 until max step pivot).map { n ->
            async {
                returnFirstFound(input, n, n + pivot, match)
            }
        }.awaitAll()
    }
    return possibleResults.min()
}

private fun returnFirstFound(input: String, start: Int, end: Int, match: String): Int {
    for (i in start until end) {
        val data = "$input$i"
        val md5hash = DigestUtils.md5Hex(data)
        val potentialMatch = md5hash.substring(0, match.length)
        if (i == 609043 || i == 1048970)
            println("data: $data, hash: $md5hash, i: $i, start: $start, end: $end")
        if (potentialMatch == match) {
            println("Found match")
            return i
        }
    }
    return Int.MAX_VALUE
}

suspend fun executeManyCoroutinesInParallelUsingAsync(): List<Int> {
    val result = coroutineScope {
        (1..5).map { n ->
            async {
                val delay = Random.nextInt(100, 1000)
                delay(delay.milliseconds)
                println("- processing $n")
                n * n
            }
        }.awaitAll()
    }
    println("Result: $result")
    return result
}
