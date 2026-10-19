package org.example

fun calculateFloor(steps: String): Int {
    var floor = 0
    for (step in steps) {
        if (step == '(')
            floor += 1
        if (step == ')')
            floor -= 1
    }
    return floor
}

/*
find the position of the first character that causes him to enter the basement (floor -1). The first character in the instructions has position 1, the second character has position 2, and so on.

For example:

) causes him to enter the basement at character position 1.
()()) causes him to enter the basement at character position 5.
 */
fun calculateFirstBasementPosition(steps: String): Int {
    var floor = 0
    steps.forEachIndexed() { i, step ->
        if (step == '(')
            floor += 1
        if (step == ')')
            floor -= 1
        if (floor == -1)
            return i + 1
    }
    return 0
}