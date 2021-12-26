fun <T> List<T>.containsOrSkipIfEmpty(element: T): Boolean {
    return if (isEmpty()) true else contains(element)
}

fun <T> tryCatch(tryAction: () -> T): T? {
    return try {
        tryAction()
    } catch (ex: Exception) {
        null
    }
}

fun List<Regex>.matchAtLeastOnce(value: String): Boolean {
    return if (isEmpty()) true else map {
        it.matches(value)
    }.contains(true)
}