# Localization-Patterns-Checker
Kotlin script to prevent invalid conversion errors in the projects with many localization files or with large amount of supported languages.

## Script setup
1. Put the local_pattern_checker-1.0.0.jar file wherever it is possible to access the project files.
2. Create conf.txt file with the following values:
    * PATH: path to files.
    * BASE: base pattern or char to identify if the string contains placeholder.
    * PATTERN: placeholder pattern.
    * REGEX-CONFLICT: characters from REGEX syntax, that are contained in the placeholders.
    * FOLDER-FILTER: only files, with parent directory, that contains this value, will be checked.
    * FILE-FILTER: only files, with names contains this value, will be checked.
3. Run the local_pattern_checker-1.0.0.jar file (be sure, that you've placed conf.txt file in the same directory).
4. If there were some errors, check errors.txt file.
5. Check result.txt file, to see the Localization-Patterns-Checker script results.

## Useful info

**Project can be very unstable:**
> Now it was only tested with %s and %1$s placeholders, but successfully passed the RTL languages check.

**Example of the conf.txt file:**
```
PATH: ./app/src/main/
PATTERN: %s
PATTERN: %1$s
REGEX-CONFLICT: $
BASE: %
FOLDER-FILTER: values
FILE-FILTER: string
```

There is **ready-to-use jar file** in the project's root folder.