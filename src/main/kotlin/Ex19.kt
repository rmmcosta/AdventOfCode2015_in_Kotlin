package org.example

import java.io.File
import kotlin.time.Clock

/*
It works by starting with some input molecule and then doing a series of replacements, 
one per step, until it has the right molecule

the machine has to be calibrated before it can be used. 
Calibration involves determining the number of molecules that can be generated in one step from a given starting point

For example, imagine a simpler machine that supports only the following replacements:

H => HO
H => OH
O => HH
Given the replacements above and starting with HOH, the following molecules could be generated:

HOOH (via H => HO on the first H).
HOHO (via H => HO on the second H).
OHOH (via H => OH on the first H).
HOOH (via H => OH on the second H).
HHHH (via O => HH).
So, in the example above, 
there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH. 
Santa's favorite molecule, HOHOHO, 
can become 7 distinct molecules (over nine replacements: six from H, and three from O).

The machine replaces without regard for the surrounding characters. 
For example, given the string H2O, the transition H => OO would result in OO2O.

Your puzzle input describes all of the possible replacements and, 
at the bottom, the medicine molecule for which you need to calibrate the machine. 
How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?
 */

fun ex19CalculateMolecules(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    val newMolecules = generateNewMolecules(sequenceOf(molecule), replacements).toSet()
    println("There are ${newMolecules.size} possible molecules.")
    return newMolecules.size
}

fun ex19StepsToFabricateMolecule(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    var steps = 0
    val guid = Clock.System.now().epochSeconds
    var fileNameToRead = "ex19_${guid}_0"
    var fileNameToWrite = "ex19_${guid}_1"
    //the first time no file exists, so we need to write the first molecule first
    writeMoleculesToFile(fileNameToRead, sequenceOf("e"))
    while (!moleculeFoundInFile(fileNameToRead, molecule)) {
        val file = File(fileNameToRead)
        file.useLines { lines ->
            var newMolecules = generateNewMolecules(lines, replacements)
            if (steps > 3)
                newMolecules = newMolecules.filter { hasCommonChars(it, molecule) }
            writeMoleculesToFile(fileNameToWrite, newMolecules)
        }
        steps++
        println("current step: $steps")
        file.delete()
        fileNameToRead = "ex19_${guid}_$steps"
        fileNameToWrite = "ex19_${guid}_${steps + 1}"
    }
    println("It took $steps steps to get the final molecule")
    return steps
}

fun hasCommonChars(molecule1: String, molecule2: String): Boolean =
    molecule1.zip(molecule2).any { it.first == it.second }

private fun moleculeFoundInFile(fileName: String, molecule: String): Boolean {
    val file = File(fileName)
    file.useLines { lines ->
        lines.forEach { line ->
            if (molecule == line)
                return true
        }
    }
    return false
}

private fun writeMoleculesToFile(fileName: String, molecules: Sequence<String>) {
    val file = File(fileName)
    file.bufferedWriter().use { out ->
        molecules.forEach { molecule ->
            out.appendLine(molecule)
        }
    }
}

private fun generateNewMolecules(
    newMolecules: Sequence<String>,
    replacements: List<Pair<String, String>>
): Sequence<String> = newMolecules.flatMap { currentMolecule ->
    calibrate(currentMolecule, replacements)
}

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<Pair<String, String>>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        parts[0].trim() to parts[1].trim()
    }
}

private fun calibrate(
    originalMolecule: String,
    replacements: List<Pair<String, String>>
): Sequence<String> {
    //the marked molecule will have a marker for the already replaced parts
    val marker = "-1"
    return sequence {
        replacements.forEach { (old, new) ->
            var markedMolecule = originalMolecule
            while (markedMolecule.contains(old)) {
                val newMarkedMolecule = markedMolecule.replaceFirst(old, new)
                markedMolecule = markedMolecule.replaceFirst(old, marker)
                yield(newMarkedMolecule.replace(marker, old))
            }
        }
    }
}