package org.example

fun ex19StepsToFabricateMolecule(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)

    try {
        val steps = calculateSteps(molecule, replacements.sortedByDescending { it.first.length })
        println("It took $steps steps to get the final molecule")
        return steps ?: throw RuntimeException("Molecule not found")
    } catch (exception: Exception) {
        println("An unexpected error occurred: ${exception.message}")
    }
    return 0
}

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<Pair<String, String>>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        parts[1].trim() to parts[0].trim()
    }
}

private fun calculateSteps(initialMolecule: String, replacements: List<Pair<String, String>>): Int? {
    val badMolecules = mutableSetOf<String>()

    fun search(molecule: String, steps: Int): Int? {
        //println("bad molecules: ${badMolecules.size}")
        println("$steps -> $molecule")
        if (molecule == "e")
            return steps


        generateNextBestGuessMolecules(molecule, replacements, badMolecules).forEach { nextMolecule ->
            val result = search(nextMolecule, steps + 1)

            if (result != null)
                return result
        }

        badMolecules.add(molecule)
        return null
    }

    return search(initialMolecule, 0)
}

private fun generateNextBestGuessMolecules(
    originalMolecule: String,
    replacements: List<Pair<String, String>>,
    badMolecules: Set<String>
): Set<String> =
    buildSet {
        replacements.forEach { (old, new) ->
            var lastIndex = originalMolecule.indexOf(old)
            while (lastIndex > -1) {
                val newCandidate =
                    originalMolecule.substring(0, lastIndex) + new + originalMolecule.substring(lastIndex + old.length)
                if (newCandidate !in badMolecules && newCandidate != originalMolecule)
                    add(newCandidate)
                lastIndex = originalMolecule.indexOf(old, lastIndex + 1)
            }
        }
    }