package org.example

import kotlin.collections.mutableListOf

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
    var previousRedChar: Char? = null
    var currentNumber = ""
    var wasDigit = false
    var rootDocumentValue: DocumentValue? = null
    var currentDocumentValue: DocumentValue? = null
    document.toCharArray().forEach { char ->
        if (char == '-')
            currentNumber += char
        if (char.isDigit()) {
            wasDigit = true
            currentNumber += char
        }
        if (!char.isDigit() && wasDigit) {
            wasDigit = false
            when (currentDocumentValue) {
                is ArrayValue -> if (currentDocumentValue.parentDocumentValue !is ObjectValue
                    || !currentDocumentValue.parentDocumentValue.isRed
                ) currentDocumentValue.values.add(
                    IntValue(currentNumber.toInt())
                )

                is ObjectValue -> if (currentDocumentValue.parentDocumentValue !is ObjectValue
                    || !currentDocumentValue.parentDocumentValue.isRed
                ) currentDocumentValue.values.add(
                    IntValue(currentNumber.toInt())
                )

                else -> throw RuntimeException("Invalid DocumentValue")
            }
            currentNumber = ""
        }

        if (char == '[') {
            val newDocumentValue = ArrayValue(currentDocumentValue)
            if (rootDocumentValue == null)
                rootDocumentValue = newDocumentValue
            else {
                when (currentDocumentValue) {
                    is ArrayValue -> currentDocumentValue.values.add(newDocumentValue)
                    is ObjectValue -> currentDocumentValue.values.add(newDocumentValue)
                    else -> throw RuntimeException("Invalid DocumentValue")
                }
            }
            currentDocumentValue = newDocumentValue
        }

        if (char == '{') {
            val newDocumentValue = ObjectValue(currentDocumentValue)
            if (rootDocumentValue == null)
                rootDocumentValue = newDocumentValue
            else {
                when (currentDocumentValue) {
                    is ArrayValue -> currentDocumentValue.values.add(newDocumentValue)
                    is ObjectValue -> currentDocumentValue.values.add(newDocumentValue)
                    else -> throw RuntimeException("Invalid DocumentValue")
                }
            }
            currentDocumentValue = newDocumentValue
        }

        if (char == '}' || char == ']') {
            currentDocumentValue = when (currentDocumentValue) {
                is ArrayValue -> currentDocumentValue.parentDocumentValue
                is ObjectValue -> currentDocumentValue.parentDocumentValue
                else -> throw RuntimeException("Invalid DocumentValue")
            }
        }

        when (char) {
            'r' if previousRedChar == null -> previousRedChar = 'r'
            'e' if previousRedChar == 'r' -> previousRedChar = 'e'
            'd' if previousRedChar == 'e' -> {
                if (currentDocumentValue is ObjectValue) {
                    currentDocumentValue.isRed = true
                }
            }

            else -> previousRedChar = null
        }
    }

    val totalSum = sumNonRedNumbers(rootDocumentValue ?: throw Exception("Invalid DocumentValue"), 0)

    println("Sum of the non red numbers: $totalSum")
    return totalSum
}

private fun sumNonRedNumbers(documentValue: DocumentValue, acc: Int): Int {
    if (documentValue is ObjectValue && documentValue.isRed) return 0
    if (documentValue is IntValue) return acc + documentValue.value
    return when (documentValue) {
        is ArrayValue -> documentValue.values.filter { it !is ObjectValue || !it.isRed }
            .sumOf { sumNonRedNumbers(it, acc) }

        is ObjectValue -> documentValue.values.filter { it !is ObjectValue || !it.isRed }
            .sumOf { sumNonRedNumbers(it, acc) }
    }
}

sealed interface DocumentValue

data class ArrayValue(
    val parentDocumentValue: DocumentValue? = null,
    val values: MutableList<DocumentValue> = mutableListOf()
) : DocumentValue

data class ObjectValue(
    val parentDocumentValue: DocumentValue? = null,
    var isRed: Boolean = false,
    val values: MutableList<DocumentValue> = mutableListOf()
) : DocumentValue

data class IntValue(val value: Int) : DocumentValue