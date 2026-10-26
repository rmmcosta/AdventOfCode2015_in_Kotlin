package org.example

/*
--- Day 13: Knights of the Dinner Table ---
- find the optimal seating arrangement
- start by writing up a list of everyone invited and the amount their happiness would increase or decrease 
if they were to find themselves sitting next to each other person
- each person will have exactly two neighbors

For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:

Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol.
Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), 
but David would gain 46 happiness units (because Alice is such a good listener), for a total change of 44.

If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54). 
Finally, seat Carol, who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). 
The arrangement looks like this:

     +41 +46
+55   David    -2
Carol       Alice
+60    Bob    +54
     -7  +83
After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, 
with a total change in happiness of 330.

What is the total change in happiness for the optimal seating arrangement of the actual guest list?
 */

var parsedConnections: Map<String, Set<Pair<String, Int>>>? = null

fun ex13CalculateTotalChangeInHappiness(potentialHappiness: String): Int {
    //this is similar to a previous exercise we just did around permutations
    //the one for the cities
    //we can build multiple trees, starting at each and then checking each possible next person 
    parsedConnections = extractConnectionsFromPotentialHappiness(potentialHappiness.trim())
    val people = parsedConnections!!.keys
    val peopleTables: List<List<Table>> = people.map { person ->
        buildTables(person, people.minus(person))
    }
    //calculate happiness levels
    val totalBestHappiness = peopleTables.maxOf { personTables ->
        personTables.maxOf { personTable ->
            personTable.seats.sumOf { seat -> seat.happinessLevel!! }
        }
    }
    println("Total best happiness: $totalBestHappiness")
    return totalBestHappiness
}

fun buildTables(person: String, people: Set<String>): List<Table> {
    val updatedTables: MutableList<Table> = mutableListOf()
    people.forEach { nextPerson ->
        val previousSeat = Seat(person)
        val nextSeat = Seat(nextPerson, previousSeat = person)
        previousSeat.nextSeat = nextPerson
        updatedTables.add(Table(person, listOf(previousSeat, nextSeat)))
    }
    var notFullTables = updatedTables.filter { table -> table.seats.size < people.size }
    while (notFullTables.isNotEmpty()) {
        notFullTables.forEach { table ->
            val peopleMissing = people - table.seats.map { seat -> seat.person }.toSet()

            val tempTables = peopleMissing.map { nextPerson ->
                val refurbishedSeats = table.seats.map { it.copy() }
                val previousSeat = refurbishedSeats.last()

                val newNextSeat = Seat(
                    nextPerson,
                    previousSeat = previousSeat.person,
                )
                previousSeat.nextSeat = nextPerson

                //if last seat
                if (peopleMissing.size == 1) {
                    val firstSeat = refurbishedSeats.first()
                    firstSeat.previousSeat = nextPerson
                    firstSeat.happinessLevel =
                        getHappiness(
                            firstSeat.person,
                            firstSeat.previousSeat!!
                        ) + getHappiness(
                            firstSeat.person,
                            firstSeat.nextSeat!!
                        )
                    newNextSeat.nextSeat = firstSeat.person
                    newNextSeat.happinessLevel =
                        getHappiness(
                            newNextSeat.person,
                            newNextSeat.previousSeat!!
                        ) + getHappiness(
                            newNextSeat.person,
                            newNextSeat.nextSeat!!
                        )
                }

                //we can now calculate the previous seat happiness
                if (previousSeat.previousSeat != null)
                    previousSeat.happinessLevel =
                        getHappiness(
                            previousSeat.person,
                            previousSeat.previousSeat!!
                        ) + getHappiness(
                            previousSeat.person,
                            previousSeat.nextSeat!!
                        )

                Table(table.root, refurbishedSeats.plus(newNextSeat))
            }
            updatedTables.remove(table)
            updatedTables.addAll(tempTables)
        }
        notFullTables = updatedTables.filter { table -> table.seats.size < people.size + 1 }
    }

    return updatedTables
}

fun extractConnectionsFromPotentialHappiness(potentialHappiness: String): Map<String, Set<Pair<String, Int>>> {
    val connections = mutableMapOf<String, Set<Pair<String, Int>>>()
    potentialHappiness.lines().forEach { line ->
        val words = line.split(" ", ".").filterNot { it.isBlank() }
        val names = words.filter { it[0].isLetter() && it[0].isUpperCase() }
        val gains = words.contains("gain")
        val happinessValue = words.find { it.toIntOrNull() != null }?.toIntOrNull()
            ?: throw IllegalArgumentException("happiness value not found in line $line")
        if (names.size != 2) throw IllegalArgumentException("Invalid line: $line")
        val (person1, person2) = names
        val connection = person2 to (if (gains) happinessValue else -happinessValue)
        if (connections.containsKey(person1))
            connections[person1] = connections[person1]!!.plus(connection)
        else
            connections[person1] = setOf(connection)
    }
    return connections
}

private fun getHappiness(person: String, otherPerson: String): Int {
    if (!parsedConnections!!.containsKey(person))
        throw IllegalArgumentException("Missing connection for person $person and next person $otherPerson")

    return parsedConnections!![person]!!.find { it.first == otherPerson }!!.second
}

data class Table(val root: String, val seats: List<Seat>)

data class Seat(
    val person: String,
    var happinessLevel: Int? = null,
    var previousSeat: String? = null,
    var nextSeat: String? = null
)