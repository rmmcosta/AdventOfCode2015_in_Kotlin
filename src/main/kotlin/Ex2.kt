package org.example

/*
list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need
every present is a box with the following surface area: 2*l*w + 2*w*h + 2*h*l
 */

fun calculatePaperOrder(dimensions: String): Int =
    //dimensions is a string with a present per line with LxWxH
    sumPerPresent(dimensions) { calculatePresentArea(it) }

private fun sumPerPresent(dimensions: String, calculateFn: (String) -> Int) =
    dimensions.split("\n").sumOf { calculateFn(it) }

private fun calculatePresentArea(dimension: String): Int {
    val (l, w, h) = getEachDimension(dimension)
    val initialArea = 2 * l * w + 2 * w * h + 2 * h * l
    //println("Initial area: $initialArea")
    //slack is the area of the smallest side.
    val slack = calculatePresentSlack(l, w, h)
    return initialArea + slack
}

private fun getEachDimension(dimension: String): Triple<Int, Int, Int> {
    //each dimension is a string with LxWxH
    val sizes = dimension.split("x")
    val l = sizes[0].toInt()
    val w = sizes[1].toInt()
    val h = sizes[2].toInt()
    return Triple(l, w, h)
}

private fun calculatePresentSlack(l: Int, w: Int, h: Int): Int {
    val (side1, side2) = getSmallestSides(l, w, h)
    return side1 * side2
}

private fun getSmallestSides(l: Int, w: Int, h: Int): Pair<Int, Int> {
    if (h < l)
        return if (w < l)
            h to w
        else
            h to l
    if (h < w)
        return h to l
    return l to w
}

/*
The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any one face.
plus a bow which feet of ribbon required for the perfect bow is equal to the cubic feet of volume of the present
 */
fun calculateRibbonLength(dimensions: String): Int =
    sumPerPresent(dimensions) { calculateRibbonPerPresent(it) }

private fun calculateRibbonPerPresent(dimension: String): Int {
    val (l, w, h) = getEachDimension(dimension)
    val (side1, side2) = getSmallestSides(l, w, h)
    val perimeter = side1 * 2 + side2 * 2
    val volume = l * w * h
    return perimeter + volume
}