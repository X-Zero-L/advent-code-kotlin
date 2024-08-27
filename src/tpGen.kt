import java.io.File

fun createDayFile(templateFile: File, dayNumber: String) {
    val dayFile = File("src/Day$dayNumber.kt")
    if (dayFile.exists()) {
        throw IllegalArgumentException("Day $dayNumber already exists.")
    }
    val pattern = Regex("""Day\d+""")
    val content = templateFile.readText().replace(pattern, "Day$dayNumber")
    dayFile.writeText(content)
    println("Day $dayNumber created.")
}

fun createInputFiles(dayNumber: String) {
    File("src/Day$dayNumber.txt").createNewFile()
    File("src/Day${dayNumber}_test.txt").createNewFile()
}

fun main() {
    val templateFile = File("src/template.kt")
    print("Enter the day number: ")
    val dayNumber = readln().toInt().toString().padStart(2, '0')
    createDayFile(templateFile, dayNumber)
    createInputFiles(dayNumber)
}