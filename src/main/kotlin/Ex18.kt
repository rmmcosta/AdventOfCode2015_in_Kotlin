package org.example

import kotlin.math.max
import kotlin.math.min

/*
Start by setting your lights to the included initial configuration (your puzzle input). 
A # means "on", and a . means "off".

Then, animate your grid in steps, where each step decides the next configuration based on the current one.

Each light's next state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals).

Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".

For example, in a simplified 6x6 grid, 
the light marked A has the neighbors numbered 1 through 8, 
and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:
1B5...
234...
......
..123.
..8A4.
..765.

The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:

A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
All of the lights update simultaneously; they all consider the same current state before moving to the next.

Here's a few steps from an example configuration of another 6x6 grid:

Initial state:
.#.#.#
...##.
#....#
..#...
#.#..#
####..

After 1 step:
..##..
..##.#
...##.
......
#.....
#.##..

After 2 steps:
..###.
......
..###.
......
.#....
.#....

After 3 steps:
...#..
......
...#..
..##..
......
......

After 4 steps:
......
......
..##..
..##..
......
......
After 4 steps, this example has four lights on.

In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
 */

fun ex18CalculateLightsOn(lightsConfiguration: String, afterStep: Int, fixedLights: List<Pair<Int, Int>>): Int {
    var currentLightsState = parseLights(lightsConfiguration, fixedLights)
    val gridSize = currentLightsState.size
    repeat(afterStep) {
        currentLightsState = currentLightsState.mapIndexed { x, lineLights ->
            lineLights.mapIndexed { y, light ->
                if ((x to y) in fixedLights)
                    true
                else {
                    val neighbors = getNeighbors(x to y, gridSize)
                    val neighborsStates = neighbors.map { (x, y) ->
                        currentLightsState[x][y]
                    }
                    getLightNextState(light, neighborsStates)
                }
            }
        }
        println()
        printLights(currentLightsState)
    }
    val lightsOn = currentLightsState.sumOf { line -> line.count { it } }
    println("After $afterStep steps there are $lightsOn lights on.")
    return lightsOn
}

private fun parseLights(lightsConfiguration: String, fixedLights: List<Pair<Int, Int>>): List<List<Boolean>> =
    lightsConfiguration.lines().mapIndexed { x, line ->
        line.toCharArray().mapIndexed { y, light ->
            (x to y) in fixedLights || light == '#'
        }
    }

private fun printLights(lights: List<List<Boolean>>) {
    lights.forEach { line ->
        line.forEach { if (it) print('#') else print('.') }
        println()
    }
}

private fun getLightNextState(light: Boolean, neighbors: List<Boolean>): Boolean {
    //A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
    //A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
    val neighborsOn = neighbors.count { it }
    return if (light) {
        neighborsOn == 3 || neighborsOn == 2
    } else {
        neighborsOn == 3
    }
}

private fun getNeighbors(currentLight: Pair<Int, Int>, gridSize: Int): List<Pair<Int, Int>> {
    val init = max(0, currentLight.first - 1) to max(0, currentLight.second - 1)
    val final = min(currentLight.first + 1, gridSize - 1) to min(currentLight.second + 1, gridSize - 1)
    return (init.first..final.first).flatMap { x ->
        (init.second..final.second).map { y ->
            x to y
        }
    }.minus(currentLight)
}