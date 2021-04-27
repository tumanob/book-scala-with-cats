/**
 * copy from 1.1
 */

sealed trait Json
final case class JsString(get: String) extends Json
case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}


/**
 * 1.2 Start
 */
// Working with type classes in Scala means working with implicit values
// and implicit parameters. There are a few rules we need to know to
// do this effectively.

// 1.2.1 Packaging Implicits
// Any definitions marked implicit in Scala must be placed inside an object
// or trait rather than at the top level


// 1.2.2 Implicit Scope
// Placing instances in a companion object to the type class has special
// significance in Scala because it plays into something called implicit scope.

import JsonWriterInstances._

Json.toJson("A string!") // TODO compiler searches for candidate type class instances by type

/**
The places where the compiler searches for candidate instances is known as
the implicit scope. The implicit scope applies at the call site; that is the
point where we call a method with an implicit parameter.
The implicit scope which roughly consists of:
  - local or inherited definitions;
  - imported definitions;
  - definitions in the companion object of the type class or the
    parameter type (in this case JsonWriter or String )
*/
// TODO uncomment below to show "ambiguous implicit values" error
//implicit val writer1: JsonWriter[String] = JsonWriterInstances.stringWriter
//implicit val writer2: JsonWriter[String] = JsonWriterInstances.stringWriter
Json.toJson("A string")




/**
we can package type class instances in roughly four ways:
1. by placing them in an object such as JsonWriterInstances ;
2. by placing them in a trait;
3. by placing them in the companion object of the type class;
4. by placing them in the companion object of the parameter type.

With option 1 we bring instances into scope by   them.
With option 2 we bring them into scope with inheritance.
With options 3 and 4 instances are always in implicit scope,
regardless of where we try to use them.
*/


// 1.2.3 Recursive Implicit Resolution
/**
The power of type classes and implicits lies in the compiler’s ability
to combine implicit definitions when searching for candidate instances.
This is sometimes known as type class composition.
*/

//TODO Problem

//implicit val optionIntWriter: JsonWriter[Option[Int]] = ???
//implicit val optionPersonWriter: JsonWriter[Option[Person]] = ???
// and so on... same for each type...  Does not scale

implicit def optionWriter[A]
(implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
  new JsonWriter[Option[A]] {
    def write(option: Option[A]): Json = option match {
      case Some(aValue) => writer.write(aValue)
      case None => JsNull
    }
  }

// How compiler search for Option[String]
// Json.toJson(Option("A string"))
// Json.toJson(Option("A string"))(optionWriter[String])
// Json.toJson(Option("A string"))(optionWriter(stringWriter))

// TODO Warning!
// Implicit Conversions
implicit def optionWriter[A]
(writer: JsonWriter[A]): JsonWriter[Option[A]] =
  ???