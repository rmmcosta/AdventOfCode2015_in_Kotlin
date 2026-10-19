package org.example

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
    return calculateWireValue(wiresMap, wire)
}

private fun calculateWireValue(wiresMap: Map<String, String>, wire: String): Int {
    if (wire.toIntOrNull() != null)
        return wire.toInt()

    val wireValue = wiresMap[wire] ?: throw IllegalArgumentException("Wire value '$wire' not present in wire map")

    return when {
        wireValue.contains(Operation.AND.name) -> {
            val calculationParts = wireValue.split(Operation.AND.name)
            calculateWireValue(wiresMap, calculationParts[0].trim()) and calculateWireValue(
                wiresMap,
                calculationParts[1].trim()
            )
        }

        wireValue.contains(Operation.OR.name) -> {
            val calculationParts = wireValue.split(Operation.OR.name)
            calculateWireValue(wiresMap, calculationParts[0].trim()) or calculateWireValue(
                wiresMap,
                calculationParts[1].trim()
            )
        }

        wireValue.contains(Operation.LSHIFT.name) -> {
            val calculationParts = wireValue.split(Operation.LSHIFT.name)
            calculateWireValue(wiresMap, calculationParts[0].trim()) shl calculationParts[1].trim().toInt()
        }

        wireValue.contains(Operation.RSHIFT.name) -> {
            val calculationParts = wireValue.split(Operation.RSHIFT.name)
            calculateWireValue(wiresMap, calculationParts[0].trim()) shr calculationParts[1].trim().toInt()
        }

        wireValue.contains(Operation.NOT.name) -> {
            val calculationParts = wireValue.split(" ")
            calculateWireValue(wiresMap, calculationParts[1].trim()).toUShort().inv().toInt()
        }

        else -> calculateWireValue(wiresMap, wireValue)
    }
}

enum class Operation {
    AND,
    OR,
    LSHIFT,
    RSHIFT,
    NOT
}

private fun getWiresMap(instructions: String): Map<String, String> = instructions.lines().associate { line ->
    val lineParts = line.split("->")
    lineParts[1].trim() to lineParts[0].trim()
}