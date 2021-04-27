// 1.6 Controlling Instance Selection

/**
When working with type classes we must consider two issues that control instance selection:
  - What is the relationship between an instance defined on a type and its subtypes?
  - How do we choose between type class instances when there are many available?
 */


// 1.6.1 Variance (Essential Scala Book - Chapter 5.6)

// Covariant
trait F[+A] // the "+" means "covariant"
// Covariance means that the type F[B] is a subtype of the type F[A] if B is a subtype of A.
// F[A] -> F[B] IF A -> B

sealed trait Shape
case class Circle(radius: Double) extends Shape
val circles: List[Circle] = ???
val shapes: List[Shape] = circles
// Generally speaking, >covariance is used for outputs<: data that we can
// later get out of a container type such as List, or otherwise returned by some method.


// Contravariance
trait F[-A] // the "-" means "contravariant"
// Perhaps confusingly, contravariance means that
// the type F[B] is a subtype of F[A] ---> if A is a subtype of B.
// F[A] -> F[B] IF B -> A

// This is useful for modelling types that represent inputs, like our JsonWriter type class:
sealed trait Json
trait JsonWriter[-A] {
  def write(value: A): Json
}

val shape: Shape = ???
val circle: Circle = ???
val shapeWriter: JsonWriter[Shape] = ???
val circleWriter: JsonWriter[Circle] = ???
def format[A](value: A, writer: JsonWriter[A]): Json = writer.write(value)
// we can’t write a Shape with circleWriter because not all Shapes are Circles.
/**

This relationship is what we formally model using contravariance.
JsonWriter[Shape] is a subtype of JsonWriter[Circle] because Circle is a subtype of Shape.
This means we can use shapeWriter anywhere we expect to see a JsonWriter[Circle].

 */

// TODO show image https://blog.knoldus.com/covariance-and-contravariance-in-scala/


// Invariance
// Easy peasy

trait F[A]

/**
This means the types F[A] and F[B] are never subtypes of one another,
no matter what the relationship between A and B.
This is the default semantics for Scala type constructors.
 */

// There are two issues that tend to arise. Let’s imagine we have an algebraic data type like:

sealed trait A
final case object B extends A
final case object C extends

/**
The issues are:
  1. Will an instance defined on a supertype be selected if one is available?
    For example, can we define an instance for A and have it work for values of type B and C?
  2. Will an instance for a subtype be selected in preference to that of
    a supertype. For instance, if we define an instance for A and B, and we have a value of type B,
    will the instance for B be selected in preference to A?
 */