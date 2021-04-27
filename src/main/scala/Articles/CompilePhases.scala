package Articles

/**
 * To get list of compile phases type:
 * `scalac -Xshow-phases`
 * to see the compiler phases result on certain phase type
 * `scalac -Xprint:<phaseName> CompilePhases.scala`
 * `scalac -Xprint:namer CompilePhases.scala`
 * namer,pickler,erasure,cleanup
 */
object CompilePhases extends App {
    val answer: String = if (1 < 2) "Yes" else "No"
    println(s"Hello,$answer world".take(2 + 3))
}
