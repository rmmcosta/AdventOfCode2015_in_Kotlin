package org.example

import kotlinx.coroutines.*
import java.lang.foreign.ValueLayout
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

/*
a street with infinite houses numbered sequentially: 1, 2, 3, 4, 5, and so on.
Each Elf is assigned a number, too, and delivers presents to houses based on that number:

The first Elf (number 1) delivers presents to every house: 1, 2, 3, 4, 5, ....
The second Elf (number 2) delivers presents to every second house: 2, 4, 6, 8, 10, ....
Elf number 3 delivers presents to every third house: 3, 6, 9, 12, 15,

Each Elf delivers presents equal to ten times his or her number at each house.

So, the first nine houses on the street end up like this:

House 1 got 10 presents.
House 2 got 30 presents.
House 3 got 40 presents.
House 4 got 70 presents.
House 5 got 60 presents.
House 6 got 120 presents.
House 7 got 80 presents.
House 8 got 150 presents.
House 9 got 130 presents.
The first house gets 10 presents: it is visited only by Elf 1, which delivers 1 * 10 = 10 presents. 
The fourth house gets 70 presents, because it is visited by Elves 1, 2, and 4, for a total of 10 + 20 + 40 = 70 presents.

What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?

Your puzzle input is 36000000.
 */

fun ex20GetHouseNumberByPresentsReceived(presentsReceived: Int): Int {
    var bestHouse = 0
    runBlocking {
        bestHouse = divideInBatches(presentsReceived)
    }
    println("The first house with $presentsReceived is house number $bestHouse")
    return bestHouse
}

private suspend fun divideInBatches(presentsReceived: Int): Int =
    coroutineScope {
        val batchSize = calculateBatchSize(presentsReceived)
        require(batchSize > 0)

        val result = CompletableDeferred<Int>()

        val jobs = (1..presentsReceived step batchSize).map { lower ->
            launch(Dispatchers.Default) {
                val upper = minOf(
                    lower.toLong() + batchSize - 1,
                    presentsReceived.toLong()
                ).toInt()

                for (house in lower..upper) {
                    ensureActive()

                    if (calculatePresentsByHouse(house) >= presentsReceived) {
                        result.complete(house)
                        break
                    }
                }
            }
        }

        // Prevent waiting forever if no house matches.
        launch {
            jobs.joinAll()
            result.completeExceptionally(
                NoSuchElementException("No matching house found")
            )
        }

        try {
            result.await()
        } finally {
            jobs.forEach { it.cancel() }
        }
    }

private fun calculateBatchSize(presentsReceived: Int): Int {
    val howManyDigits = presentsReceived.toString().length
    val batchSize = (0 until howManyDigits).fold("1") { acc, _ -> acc + "0" }.toInt()
    return max(batchSize, 1)
}

val memoization: ConcurrentHashMap<Int, Int> = ConcurrentHashMap()

private fun calculatePresentsByHouse(initialHouse: Int): Int {
    tailrec fun calculatePresentsByHouse(currentElf: Int, acc: Int): Int {
        if (currentElf == 1)
            return acc + 10

        var newCurrentHouse = currentElf - 1
        while (initialHouse % newCurrentHouse != 0)
            newCurrentHouse--

        return calculatePresentsByHouse(newCurrentHouse, acc + currentElf * 10)
    }

    if (memoization.containsKey(initialHouse))
        return memoization[initialHouse]!!

    val presents = calculatePresentsByHouse(initialHouse, 0)
    memoization[initialHouse] = presents
    return presents
}

fun ex20GetHouseNumberByPresentsReceivedPart2(presentsReceived: Int): Int {
    var currentHouse = 0
    do {
        println("current house: $currentHouse")
        currentHouse++
        val currentPresents = calculatePresentsByHousePart2(currentHouse)
        println("current presents: $currentPresents")
    } while (currentPresents < presentsReceived)
    println("The first house with $presentsReceived is house number $currentHouse")
    return currentHouse
}

private fun calculatePresentsByHousePart2(currentHouse: Int): Int {
    val result = (1..currentHouse).fold(0) { acc, elf ->
        val housesVisited = currentHouse / elf
        if (currentHouse % elf == 0 && housesVisited < 50)
            acc + elf * 11
        else
            acc
    }
    return result
}