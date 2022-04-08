import java.net.URL
import java.util.regex.Pattern
import java.util.stream.Collectors

fun main(args: Array<String>) {
    val baseURL = "https://ocw.mit.edu"
    val startingURL =
        "$baseURL/courses/6-006-introduction-to-algorithms-spring-2020/video_galleries/lecture-videos/"
    val (startsWithLayer1, containsLayer1, startsWithLayer2) = arrayOf(
        "/courses",
        "/resources/",
        "https://archive.org"
    )
    val hrefRegex = Pattern.compile("(?<=href=\")[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|](?=\")")

    hrefRegex
        .matcher(URL(startingURL).readText())
        .results()
        .forEach {
            val match = it.group()
            if (match.startsWith(startsWithLayer1) && match.contains(containsLayer1)) {
                runCatching {
                    hrefRegex
                        .matcher(URL(baseURL + match).readText())
                        .results()
                        .forEach {
                            val innerMatch = it.group()
                            if (innerMatch.startsWith(startsWithLayer2)) println(innerMatch)
                        }
                }
            }
        }
}