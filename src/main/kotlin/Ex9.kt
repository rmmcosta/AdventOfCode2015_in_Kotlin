package org.example

/*
Every year, Santa manages to deliver all of his presents in a single night.

This year, however, he has some new locations to visit; 
his elves have provided him the distances between every pair of locations. 
He can start and end at any two (different) locations he wants, but he must visit each location exactly once. 
What is the shortest distance he can travel to achieve this?

For example, given the following distances:

London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
The possible routes are therefore:

Dublin -> London -> Belfast = 982
London -> Dublin -> Belfast = 605
London -> Belfast -> Dublin = 659
Dublin -> Belfast -> London = 659
Belfast -> Dublin -> London = 605
Belfast -> London -> Dublin = 982
The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.

What is the distance of the shortest route?
 */
class Ex9(distances: String) {
    //build the dict with the cities pair as key and the distance between them as value
    private val distancesMap: Map<Pair<String, String>, Int> = distances.lines().associate {
        val parts = it.split("=")
        val cities = parts[0].trim().split("to")
        val distance = parts[1].trim().toInt()
        val city1 = cities[0].trim()
        val city2 = cities[1].trim()
        Pair(city1, city2) to distance
    }

    private val availableCities = distancesMap.keys.flatMap { it.toList() }.toSet()

    private val allPossiblePaths: MutableList<List<Triple<String, Int, Int>>> = mutableListOf()

    fun calculateShortestAndLongestRoute() {
        //build a set with all the cities
        //now we need all the possible combinations of cities -> permutations
        //factorial: 3 points -> 6 permutations; 4 points -> 24 permutations; 5 points -> 120 permutations
        //lexicographical ordering (alphabetical tracking) or a tree structure
        //we need a tree for each city where each city is the root of it's own tree
        availableCities.forEach { city ->
            getCityMap(listOf(Triple(city, 0, 0)))
        }

        allPossiblePaths.forEach { path ->
            println(path)
        }
        val shortestDistance = allPossiblePaths.minOf {
            val (_, lastDistance, accDistance) = it.last()
            lastDistance + accDistance
        }
        println("The shortest distance is $shortestDistance")
        val longestDistance = allPossiblePaths.maxOf {
            val (_, lastDistance, accDistance) = it.last()
            lastDistance + accDistance
        }
        println("The longest distance is $longestDistance")
    }

    private fun getCityMap(currentCities: List<Triple<String, Int, Int>>) {
        if (currentCities.size == availableCities.size) {
            allPossiblePaths.add(currentCities)
            return
        }
        val remainingCities = availableCities.minus(currentCities.map { it.first }.toSet())

        remainingCities.forEach { remainingCity ->
            val (lastVisitedCity, lastDistance, accDistance) = currentCities.last()
            val pair1 = lastVisitedCity to remainingCity
            val pair2 = remainingCity to lastVisitedCity
            val distance: Int =
                distancesMap[pair1] ?: distancesMap[pair2]
                ?: throw Exception("City $lastVisitedCity not connected to $remainingCity")
            getCityMap(currentCities + Triple(remainingCity, distance, accDistance + lastDistance))
        }
    }
}

data class City(val name: String, val cityConnections: List<CityConnection>)

data class CityConnection(val city: City, val distance: Int)