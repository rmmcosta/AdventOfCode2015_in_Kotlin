package org.example

import kotlin.math.max

/*
find the right balance of ingredients

Your recipe leaves room for exactly 100 teaspoons of ingredients

You make a list of the remaining ingredients you could use to finish the recipe (your puzzle input) and their properties per teaspoon:

capacity (how well it helps the cookie absorb milk)
durability (how well it keeps the cookie intact when full of milk)
flavor (how tasty it makes the cookie)
texture (how it improves the feel of the cookie)
calories (how many calories it adds to the cookie)

You can only measure ingredients in whole-teaspoon amounts accurately

The total score of a cookie can be found by adding up each of the properties (negative totals become 0) 
and then multiplying together everything except calories.

suppose you have these two ingredients:

Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
Then, choosing to use 44 teaspoons of butterscotch 
and 56 teaspoons of cinnamon (because the amounts of each ingredient must add up to 100) 
would result in a cookie with the following properties:

A capacity of 44*-1 + 56*2 = 68
A durability of 44*-2 + 56*3 = 80
A flavor of 44*6 + 56*-2 = 152
A texture of 44*3 + 56*-1 = 76

Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now) results in a total score of 62842880, 
which happens to be the best score possible given these ingredients

If any properties had produced a negative total, it would have instead become zero, 
causing the whole score to multiply to zero.
 */

const val RECIPE_TEASPOONS = 100

fun ex15CalculateRecipeScore(ingredients: String): Int {
    val ingredients = parseIngredients(ingredients)
    val combinations = generateCombinations(ingredients.size)
    val recipes = generateRecipes(combinations, ingredients)
    val scores = recipes.map { recipe ->
        recipe to calculateScore(recipe)
    }
    val maxScore = scores.maxOf { it.second }
    println("Mas scores: $maxScore")
    return maxScore
}

private fun generateRecipes(combinations: List<List<Int>>, ingredients: List<Ingredient>): List<Recipe> =
    combinations.map { combination ->
        val quantities = combination.mapIndexed { index, ingredientQty ->
            ingredientQty to ingredients[index]
        }
        Recipe(quantities)
    }

private fun generateCombinations(ingredientsQty: Int): List<List<Int>> {
    //one spoon per ingredient
    val spoons = (0 until ingredientsQty).map { 0 }
    val combinations = mutableListOf(spoons)
    val latestSpoonValues: MutableMap<Int, Int> = mutableMapOf()
    latestSpoonValues.putAll(List(spoons.size) { i -> i to 0 }.toMap())
    val currentSpoon = ingredientsQty - 1
    var validCombinations = 0
    while (!latestSpoonValues.values.all { it == RECIPE_TEASPOONS }) {
        var currentSpoonQty = latestSpoonValues[currentSpoon]!!
        var lastCombination = latestSpoonValues.keys.sorted().map { latestSpoonValues[it]!! }
        while (currentSpoonQty < RECIPE_TEASPOONS) {
            val newCombination = getNextCombination(lastCombination, currentSpoon)
            lastCombination = newCombination
            if (isValidCombination(newCombination)) {
                validCombinations++
                println("valid combinations so far: $validCombinations")
                combinations.add(newCombination)
            }
            currentSpoonQty++
        }
        var previousSpoon = currentSpoon - 1
        println("last combination: $lastCombination")
        while (previousSpoon >= 0 && lastCombination[previousSpoon] == RECIPE_TEASPOONS) {
            previousSpoon--
        }
        if (previousSpoon > -1) {
            val nextCombination = lastCombination.toMutableList()
            nextCombination[previousSpoon]++
            while (previousSpoon < ingredientsQty - 1) {
                previousSpoon++
                nextCombination[previousSpoon] = 0
            }
            println("Previous spoon: $previousSpoon, nextCombination: $nextCombination")
            if (isValidCombination(nextCombination)) {
                combinations.add(nextCombination)
            }
            nextCombination.forEachIndexed { index, spoonValue ->
                latestSpoonValues[index] = spoonValue
            }
        } else {
            println("Valid combinations: $validCombinations")
            return combinations.filter(::isValidCombination)
        }
    }
    println("Valid combinations: $validCombinations")
    return combinations.filter(::isValidCombination)
}

private fun isValidCombination(combination: List<Int>): Boolean = combination.sum() == RECIPE_TEASPOONS

private fun getNextCombination(lastCombination: List<Int>, currentSpoon: Int): List<Int> {
    val newCombination = lastCombination.toMutableList()
    newCombination[currentSpoon] = newCombination[currentSpoon] + 1
    return newCombination
}

private fun parseIngredients(ingredients: String): List<Ingredient> = ingredients.lines()
    .map { ingredientDescription ->
        val parts = ingredientDescription.split(":", ",", " ")
        val name = parts[0].trim()
        val (capacity, durability, flavor, texture, calories) = parts.filter { it.toIntOrNull() != null }
            .map { it.toInt() }
        Ingredient(name, capacity, durability, flavor, texture, calories)
    }

private fun calculateScore(recipe: Recipe): Int {
    var capacity = 0
    var durability = 0
    var flavor = 0
    var texture = 0
    recipe.quantities.forEach { (qty, ingredient) ->
        capacity += ingredient.capacity * qty
        durability += ingredient.durability * qty
        flavor += ingredient.flavor * qty
        texture += ingredient.texture * qty
    }
    return max(capacity, 0) * max(durability, 0) * max(flavor, 0) * max(texture, 0)
}

data class Recipe(val quantities: List<Pair<Int, Ingredient>>)

data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)