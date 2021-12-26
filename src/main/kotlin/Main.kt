import java.io.File

fun main() {

    val paths = mutableListOf<String>()
    val patterns = mutableListOf<String>()
    val fileFilters = mutableListOf<String>()
    val folderFilters = mutableListOf<String>()
    val regexConflict = mutableListOf<String>()

    var base: String? = null

    File("conf.txt").apply {
        try {
            val lines = readLines()

            lines.forEach { line ->
                DoubleConf(line).provide()?.let {
                    when (it) {
                        is ValueConf.Base -> base = it.value
                        is ValueConf.Path -> paths.add(it.value)
                        is ValueConf.Pattern -> patterns.add(it.value)
                        is ValueConf.FileFilter -> fileFilters.add(it.value)
                        is ValueConf.FolderFilter -> folderFilters.add(it.value)
                        is ValueConf.RegexConflict -> regexConflict.add(it.value)
                    }
                }
            }
        } catch (ex: Exception) {
            File("errors.txt").apply {
                writeText("Error with loading conf.txt file. Message = ${ex.message}")
            }
        }


    }

    val core = Localizations(
        paths = paths,
        patterns = patterns,
        basePattern = base,
        fileFilter = fileFilters,
        folderFilter = folderFilters,
        regexConflict = regexConflict
    )

    try {
        core.validate()
    } catch (ex: Exception) {
        File("errors.txt").apply {
            writeText("Error during validation. Message = ${ex.message}")
        }
    }


    File("result.txt").apply {
        val err = core.getErrors()
        writeText("Errors in localization: \n")
        err.forEach {
            appendText("$it\n")
        }
    }

}