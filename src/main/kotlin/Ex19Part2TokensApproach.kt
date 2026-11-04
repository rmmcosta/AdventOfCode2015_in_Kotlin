package org.example

const val TOKENS_REGEX = "[A-Z][a-z]?"

fun ex19StepsToFabricateMoleculeWithTokens(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    replacements.sortedByDescending { it.tokensIncrease }.forEach(::println)
    val steps = calculateSteps(molecule, replacements)
    println("It took $steps steps to get the final molecule")
    return steps
}

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<TokenReplacement>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        val old = parts[0].trim()
        val new = parts[1].trim()
        val oldTokensCount = if (old == "e") 1 else getTokens(old).count()
        val newTokensCount = getTokens(new).count()
        TokenReplacement(old, new, newTokensCount - oldTokensCount)
    }
}

/*
H -> CRnFYFYFAr = 7 tokens increase
Ca -> SiRnFYFAr = 5 tokens increase
H -> CRnFYMgAr = 5 tokens increase
H -> CRnMgYFAr = 5 tokens increase
H -> NRnFYFAr = 5 tokens increase
O -> CRnFYFAr = 5 tokens increase
Al -> ThRnFAr = 3 tokens increase
B -> TiRnFAr = 3 tokens increase
Ca -> PRnFAr = 3 tokens increase
Ca -> SiRnMgAr = 3 tokens increase
H -> CRnAlAr = 3 tokens increase
H -> NRnMgAr = 3 tokens increase
H -> ORnFAr = 3 tokens increase
N -> CRnFAr = 3 tokens increase
O -> CRnMgAr = 3 tokens increase
O -> NRnFAr = 3 tokens increase
P -> SiRnFAr = 3 tokens increase
e -> HF = 1 tokens increase
e -> NAl = 1 tokens increase
e -> OMg = 1 tokens increase
Al -> ThF = 1 tokens increase
B -> BCa = 1 tokens increase
B -> TiB = 1 tokens increase
Ca -> CaCa = 1 tokens increase
Ca -> PB = 1 tokens increase
Ca -> SiTh = 1 tokens increase
F -> CaF = 1 tokens increase
F -> PMg = 1 tokens increase
F -> SiAl = 1 tokens increase
H -> HCa = 1 tokens increase
H -> NTh = 1 tokens increase
H -> OB = 1 tokens increase
Mg -> BF = 1 tokens increase
Mg -> TiMg = 1 tokens increase
N -> HSi = 1 tokens increase
O -> HP = 1 tokens increase
O -> OTi = 1 tokens increase
P -> CaP = 1 tokens increase
P -> PTi = 1 tokens increase
Si -> CaSi = 1 tokens increase
Th -> ThCa = 1 tokens increase
Ti -> BP = 1 tokens increase
Ti -> TiTi = 1 tokens increase
 */

/*
Think about an accounting problem
Imagine I tell you:
I started with €1. Every operation adds exactly €1. I finished with €100. How many operations did I perform?
That's what we're trying to achieve with our molecule.
Unfortunately, raw token count doesn't work because:
normal rule     → +1
Rn ... Ar rule  → +3
one Y rule      → +5
two Y rule      → +7
 */
private fun calculateSteps(molecule: String, replacements: List<TokenReplacement>): Int {
    val tokens = getTokens(molecule)
    val totalTokens = tokens.count()
    println("Total tokens: $totalTokens")
    //now we need to understand how many tokens a replacement adds
    //the first step increases 2 tokens
    //most of the replacements next will take 1 token
    //except for the ones with 3, 5 and 7
    val invariantTokensIncrease = replacements
        .filter { it.tokensIncrease > 1 }
        .flatMap { getTokens(it.new).toList() }
        .groupingBy { it }
        .eachCount()

    val leftValues = replacements.map { it.old }
    val rightValues = replacements.map { it.new }

    val leftTokens = leftValues.flatMap { getTokens(it) }.toSet()
    val rightTokens = rightValues.flatMap { getTokens(it) }.toSet()

    val structuralTokens = rightTokens - leftTokens

    val countRn = tokens.count { it == "Rn" }
    val countAr = tokens.count { it == "Ar" }
    val countY = tokens.count { it == "Y" }
    return totalTokens - countRn - countAr - 2 * countY - 1 //for the e
}

private fun getTokens(molecule: String): Sequence<String> {
    val twoLettersTokens = Regex(TOKENS_REGEX)
    return twoLettersTokens.findAll(molecule).map { it.value }
}

private data class TokenReplacement(val old: String, val new: String, val tokensIncrease: Int) {
    override fun toString(): String = "$old -> $new = $tokensIncrease tokens increase"
}
