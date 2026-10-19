package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.collections.get

/*
Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). 
A signal is provided to each wire by a gate, another wire, or some specific value. 
Each wire can only get a signal from one source, but can provide its signal to multiple destinations. 
A gate provides no signal until all of its inputs have a signal.

The included instructions booklet describes how to connect the parts together:
 x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.

For example:

123 -> x means that the signal 123 is provided to wire x.
x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). 
If, for some reason, you'd like to emulate the circuit instead, 
almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.

For example, here is a simple circuit:

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
After it is run, these are the signals on the wires:

d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?
 */
fun calculateSignalOnWire(instructions: String, wire: String): Int {
    val wiresMap = getWiresMap(instructions)
    //keep looping while there are wires whose values are calculations
    while (wiresMap.values.any { it.toIntOrNull() == null }) {
        val wiresAlreadyCalculated = wiresMap.filter { it.value.toIntOrNull() != null }
        println("wires already calculated: ${wiresAlreadyCalculated.size}")
        //now let's iterate the wires map with uncalculated values and replace the wires by it's value
        val uncalculatedWires = wiresMap.filter { it.value.toIntOrNull() == null }
        println("uncalculated wires: ${uncalculatedWires.size}")
        uncalculatedWires.keys.forEach { wire ->
            wiresMap[wire] = calculateNewWireValue(wiresMap[wire]!!, wiresAlreadyCalculated)
        }
    }
    return wiresMap[wire]?.toInt() ?: 0
}

private fun calculateNewWireValue(calculation: String, wiresAlreadyCalculated: Map<String, String>): String = when {
    calculation.contains(Operation.AND.name) -> {
        updatedAndCalculation(calculation, wiresAlreadyCalculated)
    }

    calculation.contains(Operation.OR.name) -> {
        updatedOrCalculation(calculation, wiresAlreadyCalculated)
    }

    calculation.contains(Operation.LSHIFT.name) -> {
        updatedLShiftCalculation(calculation, wiresAlreadyCalculated)
    }

    calculation.contains(Operation.RSHIFT.name) -> {
        updatedRShiftCalculation(calculation, wiresAlreadyCalculated)
    }

    calculation.contains(Operation.NOT.name) -> {
        updatedNotCalculation(calculation, wiresAlreadyCalculated)
    }

    else -> calculation
}

private fun updatedAndCalculation(
    calculation: String,
    wiresAlreadyCalculated: Map<String, String>
): String {
    val parts = calculation.split(Operation.AND.name)
    var operand1: String? = parts[0].trim()
    var operand2: String? = parts[1].trim()
    val wiresAlreadyCalculatedKeys = wiresAlreadyCalculated.keys
    if (operand1 in wiresAlreadyCalculatedKeys) {
        operand1 = wiresAlreadyCalculated[operand1]
    }
    if (operand2 in wiresAlreadyCalculatedKeys) {
        operand2 = wiresAlreadyCalculated[operand2]
    }
    if (operand1?.toIntOrNull() != null && operand2?.toIntOrNull() != null) {
        return "${operand1.toInt() and operand2.toInt()}"
    }
    return "$operand1 AND $operand2"
}

private fun updatedOrCalculation(
    calculation: String,
    wiresAlreadyCalculated: Map<String, String>
): String {
    val parts = calculation.split(Operation.OR.name)
    var operand1: String? = parts[0].trim()
    var operand2: String? = parts[1].trim()
    val wiresAlreadyCalculatedKeys = wiresAlreadyCalculated.keys
    if (operand1 in wiresAlreadyCalculatedKeys) {
        operand1 = wiresAlreadyCalculated[operand1]
    }
    if (operand2 in wiresAlreadyCalculatedKeys) {
        operand2 = wiresAlreadyCalculated[operand2]
    }
    if (operand1?.toIntOrNull() != null && operand2?.toIntOrNull() != null) {
        return "${operand1.toInt() or operand2.toInt()}"
    }
    return "$operand1 OR $operand2"
}

private fun updatedLShiftCalculation(
    calculation: String,
    wiresAlreadyCalculated: Map<String, String>
): String {
    val parts = calculation.split(Operation.LSHIFT.name)
    var operand1: String? = parts[0].trim()
    val operand2: String = parts[1].trim()
    val wiresAlreadyCalculatedKeys = wiresAlreadyCalculated.keys
    if (operand1 in wiresAlreadyCalculatedKeys) {
        operand1 = wiresAlreadyCalculated[operand1]
    }
    if (operand1?.toIntOrNull() != null && operand2.toIntOrNull() != null) {
        return "${operand1.toInt() shl operand2.toInt()}"
    }
    return "$operand1 LSHIFT $operand2"
}

private fun updatedRShiftCalculation(
    calculation: String,
    wiresAlreadyCalculated: Map<String, String>
): String {
    val parts = calculation.split(Operation.RSHIFT.name)
    var operand1: String? = parts[0].trim()
    val operand2: String = parts[1].trim()
    val wiresAlreadyCalculatedKeys = wiresAlreadyCalculated.keys
    if (operand1 in wiresAlreadyCalculatedKeys) {
        operand1 = wiresAlreadyCalculated[operand1]
    }
    if (operand1?.toIntOrNull() != null && operand2.toIntOrNull() != null) {
        return "${operand1.toInt() shr operand2.toInt()}"
    }
    return "$operand1 RSHIFT $operand2"
}

private fun updatedNotCalculation(
    calculation: String,
    wiresAlreadyCalculated: Map<String, String>
): String {
    val parts = calculation.split(" ")
    var operand2: String? = parts[1].trim()
    val wiresAlreadyCalculatedKeys = wiresAlreadyCalculated.keys
    if (operand2 in wiresAlreadyCalculatedKeys) {
        operand2 = wiresAlreadyCalculated[operand2]
    }
    if (operand2?.toIntOrNull() != null) {
        return "${operand2.toUShort().inv().toInt()}"
    }
    return "NOT $operand2"
}

enum class Operation {
    AND,
    OR,
    LSHIFT,
    RSHIFT,
    NOT
}

private fun getWiresMap(instructions: String): MutableMap<String, String> = instructions.lines().associate { line ->
    val lineParts = line.split("->")
    lineParts[1].trim() to lineParts[0].trim()
}.toMutableMap()