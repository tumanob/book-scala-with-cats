// 1.5 Example: Eq
// Eq is designed to support type-safe equality and address
// annoyances using Scala’s built-in == operator.

List(1, 2, 3).map(Option(_)).filter(item => item == 1)
// warning: Option[Int] and Int are unrelated: they will
// most lik ely never compare equal
// we should have compared item to Some(1) instead of 1




// 1.5.1 Equality, Liberty, and Fraternity
//  from cats package
trait Eq[A] {
  def eqv(a: A, b: A): Boolean
  // other concrete methods based on eqv...
}

//The interface syntax, defined in cats.syntax.eq, provides two methods:
// === compares two objects for equality;
// =!= compares two objects for inequality.



// 1.5.2 Comparing Ints

import cats.Eq
import cats.instances.int._ // for Eq

val eqInt = Eq[Int]

eqInt.eqv(123, 123)
eqInt.eqv(123, 234)
123 == "234"
//eqInt.eqv(123, "234") // type mismatch error and not compiled


import cats.syntax.eq._ // for === and =!=
123 === 123
123 =!= 234
//123 === "123" // TODO uncomment for error




// 1.5.3 Comparing Options

import cats.instances.int._ // for Eq
import cats.instances.option._ // for Eq

//Some(1) === None // error: value === is not a member of Some[Int]

(Some(1) : Option[Int]) === (None : Option[Int])


// We can do this in a friendlier fashion using
// the Option.apply and Option.empty methods from the standard library:

Option(1) === Option.empty[Int]

// OR using cats.syntax.option
import cats.syntax.option._ // for some and none
1.some === none[Int]
1.some =!= none[Int]
1.some.=!=(none[Int])  // same as above
//1.some !== none[Int] // infix




//1.5.4 Comparing Custom Types

import java.util.Date
import cats.instances.long._ // for Eq

implicit val dateEq: Eq[Date] = Eq.instance[Date] {
  (date1, date2) =>
  date1.getTime === date2.getTime }


val x = new Date() // now
val y = new Date() // a bit later than now

x === x
x.===(x)
x === y
