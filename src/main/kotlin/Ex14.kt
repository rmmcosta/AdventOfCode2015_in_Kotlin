package org.example

/*
Reindeer can fly at high speeds, but must rest occasionally to recover their energy. 
Santa would like to know which of his reindeer is fastest, and so he has them race.

Reindeer can only either be flying (always at their top speed) or resting (not moving at all), 
and always spend whole seconds in either state.

For example, suppose you have the following Reindeer:

Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
After one second, Comet has gone 14 km, while Dancer has gone 16 km. After ten seconds, 
Comet has gone 140 km, while Dancer has gone 160 km. On the eleventh second, Comet begins resting (staying at 140 km), 
and Dancer continues on for a total distance of 176 km. On the 12th second, both reindeer are resting.
They continue to rest until the 138th second, when Comet flies for another ten seconds. 
On the 174th second, Dancer flies for another 11 seconds.

In this example, after the 1000th second, both reindeer are resting, 
and Comet is in the lead at 1120 km (poor Dancer has only gotten 1056 km by that point). 
So, in this situation, Comet would win (if the race ended at 1000 seconds).

Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, 
what distance has the winning reindeer traveled?
 */

fun ex14CalculateFastestReindeerDistanceTraveled(reindeerDescriptions: String, flightDuration: Int): Int {
    val reindeerList: Set<Reindeer> = getReindeerList(reindeerDescriptions)
    val flights = reindeerList.map { reindeer -> Flight(reindeer) }
    (0..flightDuration).forEach { currentSecond ->
        val inFlightReindeer = flights.filter { flight -> flight.enteredFlightAt != null }
        val atRestReindeer = flights.filter { flight -> flight.enteredRestAt != null }

        inFlightReindeer.forEach { flight ->
            //need to rest
            if ((flight.enteredFlightAt!! + flight.reindeer.velocityDuration) - currentSecond == 0) {
                flight.enteredFlightAt = null
                flight.enteredRestAt = currentSecond
            } else {
                flight.distanceTraveled += flight.reindeer.velocity
            }
        }

        atRestReindeer.forEach { flight ->
            //can resume flight
            if ((flight.enteredRestAt!! + flight.reindeer.restDuration) - currentSecond == 0) {
                flight.enteredRestAt = null
                flight.enteredFlightAt = currentSecond
                flight.distanceTraveled += flight.reindeer.velocity
            }
        }
    }
    println(flights)
    val maxDistance = flights.maxOf { flight -> flight.distanceTraveled }
    println("max distance traveled: $maxDistance")
    return maxDistance
}

private fun getReindeerList(reindeerDescriptions: String): Set<Reindeer> =
    reindeerDescriptions.lines().map(::parseReindeerDescription).toSet()

private fun parseReindeerDescription(description: String): Reindeer {
    val parts = description.split(" ")
    val name = parts[0]
    val numbers = parts.filter { it.toIntOrNull() != null }
    val (velocity, velocityDuration, restDuration) = numbers
    return Reindeer(name, velocity.toInt(), velocityDuration.toInt(), restDuration.toInt())
}

data class Reindeer(val name: String, val velocity: Int, val velocityDuration: Int, val restDuration: Int) {
    override fun toString(): String = "$name ($velocity, $velocityDuration, $restDuration)"
}

data class Flight(
    val reindeer: Reindeer,
    var distanceTraveled: Int = 0,
    var enteredFlightAt: Int? = 0,
    var enteredRestAt: Int? = null
) {
    override fun toString(): String =
        "$reindeer, distance traveled: $distanceTraveled and ${if (enteredRestAt != null) "is resting" else if (enteredFlightAt != null) "is flying" else "is in a wrong state"}"
}