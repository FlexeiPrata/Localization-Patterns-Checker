import java.io.File

class Localizations(
    val paths: List<String>,
    patterns: List<String>,
    basePattern: String?,
    fileFilter: List<String> = emptyList(),
    folderFilter: List<String> = emptyList(),
    regexConflict: List<String> = emptyList()
) {

    private val errorStrings = mutableListOf<Errors>()

    private val base = try {
        Regex(basePattern ?: "%")
    } catch (ex: Exception) {
        throw Exception("Invalid base pattern = ${ex.message}")
    }

    private val folderRegex = folderFilter.map {
        Regex(".(\\\\.*)*\\\\.*$it.*\\\\.*\\.*")
    }
    private val fileRegex = fileFilter.map {
        Regex(".*$it.*\\..*")
    }
    private val rPatterns = patterns.map { pattern ->
        try {
            var regex = pattern

            regexConflict.forEach {
                regex = regex.replace(it, "\\$it")
            }

            Regex(regex)
        } catch (ex: Exception) {
            throw Exception("Invalid pattern = ${ex.message}")
        }

    }

    private fun readAll(file: File): List<FileHolder> {
        val files = mutableListOf<FileHolder>()

        return when {
            file.isFile -> {
                tryCatch {
                    if (fileRegex.matchAtLeastOnce(file.name) && folderRegex.matchAtLeastOnce(file.path)) {
                        val text = file.readLines()
                        files.add(FileHolder(file.path, text))
                    }
                }
                files
            }
            else -> emptyList()
        }
    }

    fun validate() {
        paths.forEach { path ->
            val files = mutableListOf<FileHolder>()

            File(path).walk().forEach { file ->
                files.addAll(readAll(file))
            }

            files.forEach { file ->
                file.text.forEachIndexed { num, string ->
                    if (string.contains(base)) {
                        val baseValue = base.findAll(string).count()
                        var result = 0
                        rPatterns.forEach {
                            result += it.findAll(string).count()
                        }
                        if (baseValue != result) {
                            errorStrings.add(
                                Errors(
                                    file.filePath,
                                    num + 1,
                                    string
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun getErrors(): List<Errors> {
        return errorStrings
    }

}

data class Errors(
    val path: String,
    val row: Int,
    val error: String
) {
    override fun toString(): String {
        return "In the $path, on the $row row, error value = $error"
    }
}

data class FileHolder(
    val filePath: String,
    val text: List<String>
)