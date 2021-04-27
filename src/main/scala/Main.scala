import cats.Show
import cats.syntax.show._
import java.util.Date

/**
 * sbt
 * set scalacOptions ++=Seq("-Xprint:pickler")
 * compile
 */
object Main {
//  def main(args: Array[String]): Unit = {
//    println("Hello Scala world!")
//    println(msg)
//  }
//
//  def msg = "Scala with Cats Rocks!"


  implicit val dateShow: Show[Date] = new Show[Date] {
    def show(date: Date): String = s"${date.getTime}ms since the epoch first."
  }
  // TODO another way to do the trick
//  implicit val dateShow: Show[Date] =
//    Show.show(
//      date => s"${date.getTime}ms since the epoch second." // function
//    )

  // TODO now the compiler do that trick?
  println(new Date().show)
}
