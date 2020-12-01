package ch.mgysel.aoc.common

/**
 * inspired by https://stackoverflow.com/a/53763936/2219787
 */
fun <T> cartesianProduct(lists: List<List<T>>): List<List<T>> {
    return lists
            .fold(listOf(listOf())) { acc, list ->
                acc.flatMap { product -> list.map { element -> product + element } }
            }
}
