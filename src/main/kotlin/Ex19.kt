package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

fun ex19CalculateMolecules(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    val possibleMolecules = runBlocking { calibrate(molecule, replacements) }
    println("There are ${possibleMolecules.size} possible molecules.")
    return possibleMolecules.size
}

fun ex19StepsToFabricateMolecule(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    var steps = 0
    var newMolecules = setOf("e")
    while (molecule !in newMolecules) {
        runBlocking {
            newMolecules = getNewMoleculesAsync(newMolecules, replacements)
            steps++
            println("current step: $steps")
        }
    }
    println("It took $steps steps to get the final molecule")
    return steps
}

private suspend fun getNewMoleculesAsync(
    newMolecules: Set<String>,
    replacements: List<Pair<String, String>>
): Set<String> = coroutineScope {
    newMolecules.map { currentMolecule ->
        async {
            calibrate(currentMolecule, replacements)
        }
    }.awaitAll().flatten().toSet()
}

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<Pair<String, String>>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        parts[0].trim() to parts[1].trim()
    }
}

private suspend fun calibrate(originalMolecule: String, replacements: List<Pair<String, String>>): Set<String> {
    //the marked molecule will have a marker for the already replaced parts
    val marker = "-1"
    return coroutineScope {
        replacements.map { (old, new) ->
            async {
                val currentReplacementMolecule = mutableListOf<String>()
                var markedMolecule = originalMolecule
                while (markedMolecule.contains(old)) {
                    val newMolecule = markedMolecule.replaceFirst(old, new)
                    currentReplacementMolecule.add(newMolecule.replace(marker, old))
                    markedMolecule = markedMolecule.replaceFirst(old, marker)
                }
                currentReplacementMolecule.toSet()
            }
        }.awaitAll().flatten().toSet()
    }
}