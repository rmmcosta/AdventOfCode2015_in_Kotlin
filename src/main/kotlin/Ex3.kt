package org.example

/*
Moves are always exactly one house to the north (^), south (v), east (>), or west (<). 
After each move, he delivers another present to the house at his new location
How many houses receive at least one present?
For example:

> delivers presents to 2 houses: one at the starting location, and one to the east.
^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.

we need a coordinates system
^ increases y keeps x
v decreases y keeps x
> increases x keeps y
< decreases x keeps y

we need a set to mark the coordinates of each visited house

How many houses receive at least one present?
 */
fun calculateHousesWithPresents(directions: String): Int {
    val visitedHouses: MutableSet<Pair<Int, Int>> = mutableSetOf(0 to 0)
    var x = 0
    var y = 0
    for (direction in directions) {
        when (direction) {
            '^' -> y += 1
            'v' -> y -= 1
            '>' -> x += 1
            '<' -> x -= 1
        }
        visitedHouses.add(x to y)
    }
    return visitedHouses.size
}

/*
For example:

^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
 */
fun calculateHousesWithPresentsWithRobotSanta(directions: String): Int {
    var visitedHouses = setOf(0 to 0)
    var santaPosition = 0 to 0
    var robotPosition = 0 to 0
    var isSantaNext = true
    for (direction in directions) {
        if (isSantaNext) {
            santaPosition = calculateNewPosition(santaPosition, direction)
            visitedHouses = updatedVisitedHouses(santaPosition, visitedHouses)
            isSantaNext = false
        } else {
            robotPosition = calculateNewPosition(robotPosition, direction)
            visitedHouses = updatedVisitedHouses(robotPosition, visitedHouses)
            isSantaNext = true
        }
    }
    return visitedHouses.size
}

private fun updatedVisitedHouses(
    position: Pair<Int, Int>,
    visitedHouses: Set<Pair<Int, Int>>
): Set<Pair<Int, Int>> = visitedHouses + (position.first to position.second)

private fun calculateNewPosition(position: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
    val (x, y) = position
    return when (direction) {
        '^' -> x to y + 1
        'v' -> x to y - 1
        '>' -> x + 1 to y
        '<' -> x - 1 to y
        else -> x to y
    }
}
