package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

fun ex19StepsToFabricateMoleculeBFS(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    var steps = 0
    var newMolecules = setOf(molecule)
    while ("e" !in newMolecules) {
        newMolecules = getNewMoleculesAsync(newMolecules, replacements)
        if (newMolecules.isEmpty()) throw RuntimeException("Dead end")
        steps++
        println("current step: $steps")
    }
    println("It took $steps steps to get the final molecule")
    return steps
}

private fun getNewMoleculesAsync(
    newMolecules: Set<String>,
    replacements: List<Pair<String, String>>
): Set<String> =
    newMolecules.flatMap { currentMolecule ->
        calibrate(currentMolecule, replacements)
    }.toSet()

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<Pair<String, String>>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        parts[1].trim() to parts[0].trim()
    }
}

private fun calibrate(originalMolecule: String, replacements: List<Pair<String, String>>): Set<String> =
    buildSet {
        replacements.forEach { (old, new) ->
            var lastIndex = 0
            while (lastIndex < originalMolecule.length) {
                lastIndex = originalMolecule.indexOf(old, lastIndex)
                if (lastIndex == -1)
                    break
                add(originalMolecule.substring(0, lastIndex) + new + originalMolecule.substring(lastIndex + old.length))
                lastIndex++
            }
        }
    }