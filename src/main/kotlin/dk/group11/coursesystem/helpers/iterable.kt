package dk.group11.coursesystem.helpers

/**
 * Combines to iterables to a new iterable
 */
fun <T> Collection<T>.concat(other: Collection<T>): Collection<T> {
    val source = this.toMutableList()

    source.addAll(other)

    return source.toList()
}