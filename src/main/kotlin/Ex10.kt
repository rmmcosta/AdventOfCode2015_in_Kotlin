package org.example

/*
Today, the Elves are playing a game called look-and-say.
They take turns making sequences by reading aloud the previous sequence and using that reading as the next sequence.
For example, 211 is read as "one two, two ones", which becomes 1221 (1 2, 2 1s).

Look-and-say sequences are generated iteratively, using the previous value as input for the next step.
For each step, take the previous value, and replace each run of digits (like 111) with the number of digits (3) followed by the digit itself (1).

For example:

1 becomes 11 (1 copy of digit 1).
11 becomes 21 (2 copies of digit 1).
21 becomes 1211 (one 2 followed by one 1).
1211 becomes 111221 (one 1, one 2, and two 1s).
111221 becomes 312211 (three 1s, two 2s, and one 1).
Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?

Your puzzle input is 3113322113.
 */
fun ex10CalculateLengthLookAndSayNumberOfTimes(sequence: String, numberOfTimes: Int) {
    val finalSequence = applyLookAndSay(sequence, numberOfTimes)
    println("The final sequence length for the initial sequence $sequence applying look and say $numberOfTimes times is: ${finalSequence.length}")
}

private tailrec fun applyLookAndSay(sequence: String, numberOfTimes: Int): String {
    if (numberOfTimes == 0) return sequence
    return applyLookAndSay(ex10CalculateLookAndSay(sequence), numberOfTimes - 1)
}

fun ex10CalculateLookAndSay(sequence: String): String {
    //grab the next number
    //count the number of times it appears until the next number
    //replace the sequence by the number of times and the original number
    var char: Char? = null
    var countChar = 0
    var finalSequence = ""
    sequence.toCharArray().forEachIndexed { index, currentChar ->
        if (currentChar != char) {
            if (char != null) {
                finalSequence += "$countChar$char"
            }
            char = currentChar
            if (countChar > 0)
                countChar = 0
        }
        countChar++
        if (index == sequence.length - 1) {
            finalSequence += "$countChar$char"
        }
    }
    return finalSequence
}