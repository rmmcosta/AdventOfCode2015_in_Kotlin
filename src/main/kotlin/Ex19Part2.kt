package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

fun ex19StepsToFabricateMolecule(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    var steps = 0
    var newMolecules = setOf(molecule)
    while ("e" !in newMolecules) {
        runBlocking {
            newMolecules = getNewMoleculesAsync(newMolecules, replacements)
            if(newMolecules.isEmpty()) throw RuntimeException("Dead end")
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
        parts[1].trim() to parts[0].trim()
    }
}

private suspend fun calibrate(originalMolecule: String, replacements: List<Pair<String, String>>): Set<String> {
    //the marked molecule will have a marker for the already replaced parts
    val marker = "-1"
    val tokens = replacements.map { it.first }.toSet()
    return coroutineScope {
        replacements.map { (old, new) ->
            async {
                val currentReplacedMolecules = mutableListOf<String>()
                var markedMolecule = originalMolecule
                while (markedMolecule.contains(old)) {
                    val newMolecule = markedMolecule.replaceFirst(old, new)
                    val newReplacedMolecule = newMolecule.replace(marker, old)
                    if (
                        newReplacedMolecule == "e"
                        || newReplacedMolecule in tokens
                        || (newReplacedMolecule.length > 1 && !newReplacedMolecule.contains("e"))
                    ) currentReplacedMolecules.add(newReplacedMolecule)
                    markedMolecule = markedMolecule.replaceFirst(old, marker)
                }
                currentReplacedMolecules.toSet()
            }
        }.awaitAll().flatten().toSet()
    }
}