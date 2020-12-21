package ch.mgysel.aoc.day21

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val foodsInput = lazy {
    val data = InputData.readLines("day21.txt")
    data.parse()
}

fun solvePart1(): Any {
    val foods = foodsInput.value
    val ingredientsCausingAllergies = linkIngredientsToAllergens(foods).values.toSet()
    fun ingredientCausesNoAllergies(ingredient: String): Boolean = ingredient !in ingredientsCausingAllergies
    return foods
        .map { food -> food.ingredients.count(::ingredientCausesNoAllergies) }
        .sum()
}

fun solvePart2(): Any {
    val foods = foodsInput.value
    val allergensToIngredients = linkIngredientsToAllergens(foods)
    return allergensToIngredients
        .map { (allergen, ingredient) -> allergen to ingredient }
        .sortedBy { it.first }
        .joinToString(",") { it.second }
}

data class Food(
    val ingredients: List<String>,
    val allergens: List<String>
)

fun List<String>.parse() = this.map { line ->
    val (first, second) = line.split(" (contains ")
    val ingredients = first.split(" ")
    val allergens = second.dropLast(1).split(", ")
    Food(ingredients, allergens)
}

fun linkIngredientsToAllergens(
    foods: List<Food>
): Map<String, String> {
    val allAllergens = foods.flatMap { it.allergens }.distinct()
    val allAllergensToIdentify = allAllergens.toMutableSet()
    val allergenToIngredients = mutableMapOf<String, String>()
    while (allAllergensToIdentify.isNotEmpty()) {
        val identifiedAllergens = mutableListOf<String>()
        allAllergensToIdentify.forEach { allergen ->
            val ingredients = foods.filter { it.allergens.contains(allergen) }
                .map { it.ingredients }
                // remove ingredients already linked to an allergen
                .map { ingredients -> ingredients.filter { !allergenToIngredients.containsValue(it) }.toSet() }
                .reduce { acc, list -> acc.intersect(list) }
            if (ingredients.size == 1) {
                allergenToIngredients[allergen] = ingredients.first()
                identifiedAllergens.add(allergen)
            }
        }
        allAllergensToIdentify.removeAll(identifiedAllergens)
    }
    return allergenToIngredients
}