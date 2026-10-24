package org.example

import javax.swing.plaf.TableHeaderUI

/*
--- Day 12: JSAbacusFramework.io ---
Santa's Accounting-Elves need help balancing the books after a recent order. 
Unfortunately, their accounting software uses a peculiar storage format. That's where you come in.

They have a JSON document which contains a variety of things: arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings. 
Your first job is to simply find all of the numbers throughout the document and add them together.

For example:

[1,2,3] and {"a":2,"b":4} both have a sum of 6.
[[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
{"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
[] and {} both have a sum of 0.
You will not encounter any strings containing numbers.

What is the sum of all numbers in the document?
 */

fun ex12SumAllNumbers(document: String): Int {
    val numbers = document.replace(Regex("[^-0-9]"), ";")
        .split(";")
        .filter { it.toIntOrNull() != null }
        .map { it.toInt() }
    val sum = numbers.sum()
    println("Sum: $sum")
    return sum
}

fun ex12SumAllNonRedNumbers(document: String): Int {
    val openObjects: MutableList<Boolean> = mutableListOf()//true is red, false is non red
    val numbersToAdd: MutableList<MutableList<Int>> = mutableListOf()
    var accumulator = 0
    var previousRedChar: Char? = null
    var currentNumber = ""
    var wasDigit = false
    document.toCharArray().forEach { char ->
        if (char == '-')
            currentNumber += char
        if (char.isDigit()) {
            wasDigit = true
            currentNumber += char
        }
        if (!char.isDigit() && wasDigit) {
            wasDigit = false
            if (numbersToAdd.isEmpty())
                numbersToAdd.add(mutableListOf())
            numbersToAdd.last().add(currentNumber.toInt())
            currentNumber = ""
        }

        if (char == '{') {
            if (openObjects.isEmpty()) {
                accumulator += numbersToAdd.flatten().sum()
                numbersToAdd.clear()
            } else
                numbersToAdd.add(mutableListOf())
            openObjects.add(false)//initially is a non red object
        }
        if (char == '}' && openObjects.isNotEmpty()) {
            //if none of the open objects are red/true
            if (openObjects.none { it } && numbersToAdd.isNotEmpty())
                accumulator += numbersToAdd.last().sum()
            if (numbersToAdd.isNotEmpty())
                numbersToAdd.removeLast()
            openObjects.removeLast()
        }
        when (char) {
            'r' if previousRedChar == null -> previousRedChar = 'r'
            'e' if previousRedChar == 'r' -> previousRedChar = 'e'
            'd' if previousRedChar == 'e' -> {
                //if inside object which was initially non red is a red after all
                if (openObjects.isNotEmpty()) {
                    openObjects.removeLast()
                    openObjects.add(true)
                }
                previousRedChar = null
            }

            else -> previousRedChar = null
        }
    }
    if (numbersToAdd.isNotEmpty())
        accumulator += numbersToAdd.flatten().sum()

    println("Sum of the non red numbers: $accumulator")
    return accumulator
}