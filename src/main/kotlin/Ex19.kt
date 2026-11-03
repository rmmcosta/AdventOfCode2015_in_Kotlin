package org.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists
import kotlin.time.Clock

const val FILES_DIR = "/Users/"

fun ex19StepsToFabricateMoleculePrevious(moleculesDescription: String): Int {
    val (molecule, replacements) = parseMoleculesDescription(moleculesDescription)
    var foundAndStep = false to 0
    val guid = Clock.System.now().epochSeconds
    val fileBaseName = "$FILES_DIR/ex19_${guid}/"
    val path = Path(fileBaseName)
    if (!path.exists())
        path.createDirectory()
    val fileNameToRead = "${fileBaseName}0"
    //the first time no file exists, so we need to write the first molecule first
    //the first line is the step
    File(fileNameToRead).writeText("0\ne")

    val filesQueue = mutableListOf(fileNameToRead)

    while (!foundAndStep.first) {
        //each line of the current file will be handled in a separated thread, which can 
        //lean to a found or not
        //if found then stop all threads and return the step
        runBlocking {
            val newFiles = coroutineScope {
                var found = false
                filesQueue.map { file ->
                    async {
                        if (found) emptyList()
                        else {
                            val newPartialFiles = parseFileAndCreateNewOnes(
                                file,
                                replacements,
                            )
                            newPartialFiles.forEach { file ->
                                async {
                                    val currentFoundAndStep = electronFoundInFile(file)
                                    if (!foundAndStep.first && currentFoundAndStep.first) {
                                        foundAndStep = currentFoundAndStep
                                        found = true
                                        return@async
                                    }
                                }
                                if (found)
                                    return@forEach
                            }
                            if (found)
                                return@async emptyList()
                            newPartialFiles
                        }
                    }
                }
            }.awaitAll().flatten()

            filesQueue.forEach { File(it).delete() }
            filesQueue.clear()

            filesQueue.addAll(newFiles)
        }
        if (filesQueue.isEmpty())
            break
    }

    filesQueue.forEach { File(it).delete() }
    if (foundAndStep.first) {
        println("It took ${foundAndStep.second} steps to get the final molecule")
    } else {
        println("Molecule not found")
    }
    return foundAndStep.second
}

private suspend fun parseFileAndCreateNewOnes(
    fileNameToRead: String,
    replacements: List<Pair<String, String>>,
): List<String> {
    val fileRead = File(fileNameToRead)
    val newFiles = mutableListOf<String>()
    try {
        val reader = BufferedReader(withContext(Dispatchers.IO) {
            FileReader(fileRead)
        })
        val steps = withContext(Dispatchers.IO) {
            reader.readLine()
        }.toInt() + 1

        println("step number: $steps")

        var line = try {
            withContext(Dispatchers.IO) {
                reader.readLine()
            }
        } catch (_: Exception) {
            null
        }

        var batchNumber = 0

        while (line != null) {
            val previousMolecules = mutableSetOf(line)
            val batchSize = 500
            var lines = 1
            while (lines <= batchSize && line != null) {
                line = try {
                    withContext(Dispatchers.IO) {
                        reader.readLine()
                    }
                } catch (_: Exception) {
                    null
                }
                if (line != null) {
                    previousMolecules.add(line)
                }
                lines++
            }

            val fileWriteName = "${fileNameToRead}_${batchNumber}"

            if (previousMolecules.isNotEmpty()) {
                createFileAndStoreNewMolecules(
                    previousMolecules,
                    fileWriteName,
                    steps,
                    replacements,
                )
                newFiles.add(fileWriteName)
                batchNumber++
            }

            line = try {
                withContext(Dispatchers.IO) {
                    reader.readLine()
                }
            } catch (_: Exception) {
                null
            }
        }

        withContext(Dispatchers.IO) {
            reader.close()
        } // Important: Close the reader after use
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }
    fileRead.delete()
    return newFiles
}

private suspend fun createFileAndStoreNewMolecules(
    previousMolecules: Set<String>,
    fileWriteName: String,
    steps: Int,
    replacements: List<Pair<String, String>>,
) {
    val fileWrite = File(fileWriteName)
    val writer = BufferedWriter(withContext(Dispatchers.IO) {
        val fileWriter = FileWriter(fileWrite, true)
        fileWriter.write("$steps\n")
        fileWriter
    })
    calibrateAsync(previousMolecules, replacements)
        .forEach { newMolecule ->
            writer.appendLine(newMolecule)
        }
    withContext(Dispatchers.IO) {
        writer.close()
    }
}

private fun electronFoundInFile(fileName: String): Pair<Boolean, Int> {
    val file = File(fileName)
    var step: Int? = null
    file.useLines { lines ->
        lines.forEach { line ->
            if (step == null && line.toIntOrNull() != null)
                step = line.toInt()
            if (line == "e")
                return true to step!!
        }
    }
    return false to -1
}

private fun parseMoleculesDescription(moleculesDescription: String): Pair<String, List<Pair<String, String>>> {
    val lines = moleculesDescription.lines().filter { it.isNotBlank() }
    val molecule = lines.last()
    return molecule to (lines - molecule).map { line ->
        val parts = line.split("=>")
        parts[1].trim() to parts[0].trim()
    }
}

private suspend fun calibrateAsync(
    originalMolecules: Set<String>,
    replacements: List<Pair<String, String>>,
): Set<String> = coroutineScope {
    val newMolecules = originalMolecules.map {
        async {
            calibrateAsync(it, replacements)
        }
    }
    newMolecules
}.awaitAll().flatten().toSet()

private suspend fun calibrateAsync(
    originalMolecule: String,
    replacements: List<Pair<String, String>>
): Set<String> {
    //the marked molecule will have a marker for the already replaced parts
    val marker = "-1"
    return coroutineScope {
        val replacedMolecules = replacements.map { (old, new) ->
            async {
                val newMolecules = mutableListOf<String>()
                var markedMolecule = originalMolecule
                while (markedMolecule.contains(old)) {
                    val newMarkedMolecule = markedMolecule.replaceFirst(old, new)
                    newMolecules.add(newMarkedMolecule.replace(marker, old))
                    markedMolecule = markedMolecule.replaceFirst(old, marker)
                }
                newMolecules.toSet()
            }
        }.awaitAll().filter { it.isNotEmpty() }
        replacedMolecules.flatten().toSet()
    }
}