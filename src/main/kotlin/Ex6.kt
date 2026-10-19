package org.example

/*
deploy one million lights in a 1000x1000 grid
how to display the ideal lighting configuration:
Lights in your grid are numbered from 0 to 999 in each direction; 
the lights at each corner are at 0,0, 0,999, 999,999, and 999,0
The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. 
Each coordinate pair represents opposite corners of a rectangle, inclusive; 
a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. 
The lights all start turned off.

To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.

For example:

turn on 0,0 through 999,999 would turn on (or leave on) every light.
toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
After following the instructions, how many lights are lit?

instructions example:
toggle 461,550 through 564,900
turn off 370,39 through 425,839
turn off 464,858 through 833,915
turn off 812,389 through 865,874
turn on 599,989 through 806,993
turn on 376,415 through 768,548
turn on 606,361 through 892,600
 */

enum class Command {
    TOGGLE,
    TURN_ON,
    TURN_OFF,
}

fun calculateLightsOn(instructions: String): Int {
    val lights: Array<Array<Boolean>> = Array(1000) { Array(1000) { false } }
    for (instruction in instructions.split("\n")) {
        val (command, start, end) = parseInstruction(instruction)
        executeCommand(command, start, end, lights)
    }
    return lights.sumOf { line -> line.count { it } }
}

private fun executeCommand(
    command: Command,
    startPosition: Pair<Int, Int>,
    endPosition: Pair<Int, Int>,
    lights: Array<Array<Boolean>>
) {
    for (x in startPosition.first..endPosition.first) {
        for (y in startPosition.second..endPosition.second) {
            lights[x][y] = when (command) {
                Command.TOGGLE -> !lights[x][y]
                Command.TURN_ON -> true
                Command.TURN_OFF -> false
            }
        }
    }
}

private fun parseInstruction(instruction: String): Triple<Command, Pair<Int, Int>, Pair<Int, Int>> {
    val instructionParts = instruction.split(" ")
    return when (instructionParts[0]) {
        "toggle" -> Triple(
            Command.TOGGLE,
            extractPosition(instructionParts[1]),
            extractPosition(instructionParts[3])
        )

        "turn" -> Triple(
            if (instructionParts[1] == "on") Command.TURN_ON else Command.TURN_OFF,
            extractPosition(instructionParts[2]),
            extractPosition(instructionParts[4])
        )

        else -> throw IllegalArgumentException("Unknown command: $instructionParts")
    }
}

private fun extractPosition(string: String): Pair<Int, Int> {
    val (x, y) = string.split(",")
    return x.toInt() to y.toInt()
}

/*
The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all start at zero.

The phrase turn on actually means that you should increase the brightness of those lights by 1.

The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.

The phrase toggle actually means that you should increase the brightness of those lights by 2.

What is the total brightness of all lights combined after following Santa's instructions?

For example:

turn on 0,0 through 0,0 would increase the total brightness by 1.
toggle 0,0 through 999,999 would increase the total brightness by 2000000.
 */
fun calculateTotalBrightness(instructions: String): Int {
    val lights: Array<Array<Int>> = Array(1000) { Array(1000) { 0 } }
    for (instruction in instructions.split("\n")) {
        val (command, start, end) = parseInstruction(instruction)
        executeBrightnessCommand(command, start, end, lights)
    }
    return lights.sumOf { line -> line.sumOf { it } }
}

private fun executeBrightnessCommand(
    command: Command,
    startPosition: Pair<Int, Int>,
    endPosition: Pair<Int, Int>,
    lights: Array<Array<Int>>
) {
    for (x in startPosition.first..endPosition.first) {
        for (y in startPosition.second..endPosition.second) {
            lights[x][y] = when (command) {
                Command.TOGGLE -> lights[x][y] + 2
                Command.TURN_ON -> lights[x][y] + 1
                Command.TURN_OFF -> if (lights[x][y] > 0) lights[x][y] - 1 else 0
            }
        }
    }
}
