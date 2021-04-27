// 1.4 Meet Cats
// In this section we will look at how type classes are implemented in Cats.

trait Show[A] {
  def show(value: A): String
}

// 1.4.1 Importing Type Classes

import cats.Show
import cats.instances.int._
import cats.instances.string._ // for Show

val showInt = Show.apply[Int] // TODO OOPS Can not find implicits
val showString: Show[String] = Show.apply[String]

showInt.show(123) // res0: String = 123
showString.show("abc")


// 1.4.3 Importing Interface Syntax
/**
We can make Show easier to use by importing the interface syntax
from cats.syntax.show . This adds an extension method called show
to any type for which we have an instance of
Show in scope:
 */

import cats.syntax.show._ // for show

123.show
"abc".show


// 1.4.4 Importing All The Things!

import cats._ // imports all of Cats’ type classes in one go;
import cats.implicits._ // imports all of the standard type class instances and all of the syntax in one go.


// 1.4.5 Defining Custom Instances
import java.util.Date

implicit val dateShow: Show[Date] = new Show[Date] {
  def show(date: Date): String = s"${date.getTime}ms since the epoch first."
}
// TODO another way to do the trick
//implicit val dateShow: Show[Date] =
//  Show.show(
//    date => s"${date.getTime}ms since the epoch second."
//  ) // function

/** creates an instance of [[Show]] using the provided function */
def show[A](f: A => String): Show[A] = new Show[A] {
  def show(a: A): String = f(a)
}
// TODO now the compiler do that trick?
new Date().show

// 1.4.6 Exercise: Cat Show
/**
 * Re-implement the Cat application from the previous section using Show instead of Printable
 */
