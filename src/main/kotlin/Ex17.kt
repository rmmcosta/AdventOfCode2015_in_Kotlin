package org.example

/*
150 litters of eggnog
split into containers
containers of size 20, 15, 10, 5, and 5
If you need to store 25 liters, there are four ways to do it:
15 and 10
20 and 5 (the first 5)
20 and 5 (the second 5)
15, 5, and 5

Filling all containers entirely, 
how many different combinations of containers can exactly fit all 150 liters of eggnog?
 */

fun ex17CalculateContainersCombinations(containersList: String, eggnogAmount: Int): Pair<Int, Int> {
    val containersList = parseContainers(containersList)
    val containersCombinations: List<List<Pair<Int, Int>>> =
        generateContainersCombinations(containersList, eggnogAmount)
    println("There are ${containersCombinations.size} possible combinations.")
    val minNumOfContainers = containersCombinations.minOf { combination -> combination.count { it.second != 0 } }
    val minNumOfCombinations =
        containersCombinations.count { combination -> combination.count { it.second != 0 } == minNumOfContainers }
    println("Minimum number of combinations: $minNumOfCombinations")
    return containersCombinations.size to minNumOfCombinations
}

private fun generateContainersCombinations(
    emptyContainers: List<Pair<Int, Int>>,
    eggnogAmount: Int
): List<List<Pair<Int, Int>>> {
    val containersCombinations: MutableList<List<Pair<Int, Int>>> = mutableListOf()
    var lastQtyPerContainer = emptyContainers
    while (!lastQtyPerContainer.all { it.first == it.second }) {
        val newCombination = getNextCombination(lastQtyPerContainer)
        if (isValidCombination(newCombination, eggnogAmount))
            containersCombinations.add(newCombination)
        lastQtyPerContainer = newCombination
    }
    return containersCombinations
}

private fun getNextCombination(lastCombination: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val nextCombination = lastCombination.toMutableList()
    var containerIndex = lastCombination.size - 1
    var container: Pair<Int, Int>
    do {
        container = nextCombination[containerIndex]
        if (container.second != 0) {
            //empty the container
            nextCombination[containerIndex] = container.first to 0
            containerIndex--
        }
    } while (containerIndex >= 0 && container.first == container.second)

    //fill in the container
    if (containerIndex > -1)
        nextCombination[containerIndex] = container.first to container.first

    return nextCombination
}

private fun isValidCombination(combination: List<Pair<Int, Int>>, eggnogAmount: Int): Boolean =
    combination.sumOf { it.second } == eggnogAmount

private fun parseContainers(containersList: String): List<Pair<Int, Int>> =
    containersList.lines().map { it.trim().toInt() to 0 }