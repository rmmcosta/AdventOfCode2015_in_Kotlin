package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

fun ex19CalculateMolecules(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    val possibleMolecules = runBlocking { calibrate(molecule, replacements) }
    println("There are ${possibleMolecules.size} possible molecules.")
    return possibleMolecules.size
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