package org.example

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
    val possibleMolecules = calibrate(molecule, replacements)
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

private fun calibrate(originalMolecule: String, replacements: List<Pair<String, String>>): Set<String> {
    val possibleMolecules = mutableSetOf<String>()
    //the marked molecule will have a marker for the already replaced parts
    var markedMolecule = originalMolecule
    val marker = "-1"
    replacements.forEach { (old, new) ->
        while (markedMolecule.contains(old)) {
            val newMolecule = markedMolecule.replaceFirst(old, new)
            possibleMolecules.add(newMolecule.replace(marker, old))
            markedMolecule = markedMolecule.replaceFirst(old, marker)
        }
        markedMolecule = originalMolecule
    }
    return possibleMolecules
}