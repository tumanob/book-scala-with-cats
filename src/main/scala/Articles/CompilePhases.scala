package Articles

import cats.Show
import cats.syntax.show._

import java.util.Date // for Show

/**
 * >>> To get list of compile phases type:
 * `scalac -Xshow-phases`
 *
 * >>> to see the compiler phases result on certain phase type
 * `scalac -Xprint:<phaseName> CompilePhases.scala`
 * `scalac -Xprint:namer CompilePhases.scala`
 * namer,pickler,erasure,cleanup
 *
 * >>> for SBT Main.scala Class to compile and see stages:
 * set scalacOptions ++=Seq("-Xprint:pickler") // where setting for scalac is set
 * compile
 */
object CompilePhases extends App {
   // val answer: String = if (1 < 2) "Yes" else "No"
   // println(s"Hello,$answer world".take(2 + 3))

    implicit val dateShow: Show[Date] = new Show[Date] {
        def show(date: Date): String = s"${date.getTime}ms since the epoch first."
    }
    // TODO another way to do the trick
    //implicit val dateShow: Show[Date] =
    //  Show.show(date => s"${date.getTime}ms since the epoch second." // function

    // TODO now the compiler do that trick?
    println(new Date().show)
}
