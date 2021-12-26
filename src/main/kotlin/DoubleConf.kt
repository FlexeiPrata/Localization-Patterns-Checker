class DoubleConf(private val line: String) {

    fun provide(): ValueConf? {
        val data = line.split(":", limit = 2).map {
            it.trim()
        }

        return if (data.size > 1) {
            when (data[0]) {
                PATH -> ValueConf.Path(data[1])
                BASE -> ValueConf.Base(data[1])
                PATTERN -> ValueConf.Pattern(data[1])
                FOLDER_FILTER -> ValueConf.FolderFilter(data[1])
                FILE_FILTER -> ValueConf.FileFilter(data[1])
                REGEX_CONFLICT -> ValueConf.RegexConflict(data[1])
                else -> null
            }
        } else null
    }

    companion object {
        const val PATH = "PATH"
        const val BASE = "BASE"
        const val PATTERN = "PATTERN"
        const val FOLDER_FILTER = "FOLDER-FILTER"
        const val FILE_FILTER = "FILE-FILTER"
        const val REGEX_CONFLICT = "REGEX-CONFLICT"
    }

}

sealed class ValueConf{
    data class Path(val value: String): ValueConf()
    data class Base(val value: String): ValueConf()
    data class Pattern(val value: String): ValueConf()
    data class FileFilter(val value: String): ValueConf()
    data class FolderFilter(val  value: String): ValueConf()
    data class RegexConflict(val value: String): ValueConf()
}