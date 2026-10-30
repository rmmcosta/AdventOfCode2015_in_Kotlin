package org.example

import kotlin.let
import kotlin.reflect.full.memberProperties

/*
these are what the MFCSAM can detect:

Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.

children: 3
cats: 7
samoyeds: 2
pomeranians: 3
akitas: 0
vizslas: 0
goldfish: 5
trees: 3
cars: 2
perfumes: 1

list of the things you can remember about each Aunt Sue. 
Things missing from your list aren't zero - you simply don't remember the value.

What is the number of the Sue that got you the gift?
*/

fun ex16CalculateAuntSureNumber(auntSuesDescriptions: String) {
    val auntSues = parseAuntSues(auntSuesDescriptions)
    val expectedDetectionResult = DetectionResult(
        children = 3,
        cats = 7,
        samoyeds = 2,
        pomeranians = 3,
        akitas = 0,
        vizslas = 0,
        goldfish = 5,
        trees = 3,
        cars = 2,
        perfumes = 1,
    )
    val possibleAuntSues = getPossibleAuntSues(auntSues, expectedDetectionResult)
    val name = possibleAuntSues.first().name
    println("You are looking for $name")
}

private fun getPossibleAuntSues(auntSues: List<AuntSue>, expectedDetectionResult: DetectionResult): List<AuntSue> =
    //given all known characteristics check if this can be the aunt sue we are looking for
    auntSues.filter { auntSue ->
        auntSue.characteristics.children?.equals(expectedDetectionResult.children) ?: true
                && auntSue.characteristics.cats?.let { it > expectedDetectionResult.cats!! } ?: true
                && auntSue.characteristics.samoyeds?.equals(expectedDetectionResult.samoyeds) ?: true
                && auntSue.characteristics.pomeranians?.let { it < expectedDetectionResult.pomeranians!! } ?: true
                && auntSue.characteristics.akitas?.equals(expectedDetectionResult.akitas) ?: true
                && auntSue.characteristics.vizslas?.equals(expectedDetectionResult.vizslas) ?: true
                && auntSue.characteristics.goldfish?.let { it < expectedDetectionResult.goldfish!! } ?: true
                && auntSue.characteristics.trees?.let { it > expectedDetectionResult.trees!! } ?: true
                && auntSue.characteristics.cars?.equals(expectedDetectionResult.cars) ?: true
                && auntSue.characteristics.perfumes?.equals(expectedDetectionResult.perfumes) ?: true
    }

private fun parseAuntSues(descriptions: String): List<AuntSue> = descriptions.lines().map { line ->
    val parts = line.split(":", ",").filterNot { it.isBlank() }.map { it.trim() }
    val name = parts[0]

    val children: Int? = getCharacteristic(parts, "children")
    val cats: Int? = getCharacteristic(parts, "cats")
    val samoyeds: Int? = getCharacteristic(parts, "samoyeds")
    val pomeranians: Int? = getCharacteristic(parts, "pomeranians")
    val akitas: Int? = getCharacteristic(parts, "akitas")
    val vizslas: Int? = getCharacteristic(parts, "vizslas")
    val goldfish: Int? = getCharacteristic(parts, "goldfish")
    val trees: Int? = getCharacteristic(parts, "trees")
    val cars: Int? = getCharacteristic(parts, "cars")
    val perfumes: Int? = getCharacteristic(parts, "perfumes")

    AuntSue(
        name,
        DetectionResult(children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes)
    )
}

private fun getCharacteristic(parts: List<String>, characteristicName: String): Int? {
    val index = parts.indexOf(characteristicName).let { if (it == -1) null else it + 1 }
    return index?.let { return parts[index].toInt() }
}

data class AuntSue(val name: String, val characteristics: DetectionResult)

data class DetectionResult(
    val children: Int?,
    val cats: Int?,
    val samoyeds: Int?,
    val pomeranians: Int?,
    val akitas: Int?,
    val vizslas: Int?,
    val goldfish: Int?,
    val trees: Int?,
    val cars: Int?,
    val perfumes: Int?,
)