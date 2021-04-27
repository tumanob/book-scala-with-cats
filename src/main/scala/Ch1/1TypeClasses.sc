/** 1.1 Anatomy of a Type Class

Type classes are a programming pattern originating in Haskell.

Benefits
They allow us to extend existing libraries with new functionality,
without using traditional inheritance, and without altering the original
library source code.

Additional articles:
https://alvinalexander.com/scala/fp-book/type-classes-101-introduction/
https://alvinalexander.com/scala/fp-book/algebraic-data-types-adts-in-scala/


There are three important components to the type class pattern:
  the type class itself,
  instances for particular types,
  and the methods that use type classes.

Type classes in Scala are implemented using >implicit values< and >parameters<,
and optionally using >implicit classes<.

Scala language constructs correspond to the components of type classes as follows:
  traits: type classes;
  implicit values: type class instances;
  implicit parameters: type class use; and
  implicit classes: optional utilities that make type classes easier to use.
 */








// 1.1.1 The Type Class
// Define a very simple JSON abstract syntax tree (AST)
// TODO - Algebraic Data Types (ADT) - The Sum? Product type? Hybrid types?
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

// The "serialize to JSON" behaviour is encoded in this trait-
// >Type class< - with at least one generic parameter (a generic “type”)
trait JsonWriter[A] {
  def write(value: A): Json
}

/**
Type classes consist of three components: (from Alex Alvin post)

-The type class, which is defined as a trait that takes at
  least one generic parameter (a generic “type”)
- Instances of the type class for types you want to extend
- Interface methods you expose to users of your new API
 */




// 1.1.2 Type Class Instances
/**
 * In Scala we define instances by creating concrete implementations of the type class
 * and tagging them with the implicit keyword:
 */
final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] = // These are known as implicit values.
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }
  implicit val personWriter: JsonWriter[Person] = new JsonWriter[Person] { // These are known as implicit values.
    def write(value: Person): Json = JsObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email) ))
  }

  // etc...
}






// 1.1.3 Type Class Use
// Cats provides utilities that make type classes easier to use,
// and you will sometimes seem these patterns in other libraries.
// There are two ways it does this: Interface Objects and Interface Syntax.

//Interface Objects
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = // TODO Method with multiple param group - one is implicit
    w.write(value)
}

/**
 * To use this object ^ , we import any type class instances we care about
 * and call the relevant method: */
import JsonWriterInstances._ // TODO comment the line to show how implicit works

Json.toJson(Person("Dave", "dave@example.com"))

/**The compiler spots that we’ve called the toJson method without
providing the implicit parameters. It tries to fix this by searching for
type class instances of the relevant types and inserting them at the call site:
Json.toJson(Person("Dave", "dave@example.com"))(personWriter)
*/

// Interface Syntax

object JsonSyntax { // TODO Why wrap in object ?
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }
}

//import JsonWriterInstances._ // this is already imported above
import JsonSyntax._
//Json.toJson(Person("Dave", "dave@example.com"))
Person("Dave", "dave@example.com").toJson

// Compiler resolution steps
// Person("Dave", "dave@example.com").{JsonWriterOps.toJson(JsonWriter[A])}
//Person("Dave", "dave@example.com").{JsonWriterOps.toJson(personWriter)}


// ===== The implicitly Method ======

//Most type classes in Cats provide other means to summon instances.
//However, implicitly is a good fallback for debugging purposes.

//def implicitly[A](implicit value: A): A = value
implicitly[JsonWriter[Person]] // todo check if there is error
// summoner  - check it.

/**
Key points

As demonstrated, a type class consists of three components:

The type class itself, which is defined as a trait that takes
  at least one generic parameter
Instances of the type class for the data types you want to extend
Interface methods you expose to users of your new API
The benefits of using a type class are:

It provides an approach that lets you add new behavior
to existing classes without using traditional inheritance,
especially in the case where you can’t (or don’t want to)
modify the existing source code of existing data types

 */