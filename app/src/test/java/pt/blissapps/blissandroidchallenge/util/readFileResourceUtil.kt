package pt.blissapps.blissandroidchallenge.util

import java.io.InputStreamReader

fun readResourceFile(fileName: String, javaClass: ClassLoader?): String {
    val inputStream = javaClass?.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("Arquivo não encontrado: $fileName")
    return InputStreamReader(inputStream).readText()
}