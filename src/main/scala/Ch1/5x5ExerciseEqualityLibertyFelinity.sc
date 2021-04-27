// 1.5.5 Exercise: Equality, Liberty, and Felinity

//Implement an instance of Eq for our running Cat example:
final case class Cat(name: String, age: Int, color: String)

//Use this to compare the following pairs of objects for equality and inequality:
val cat1 = Cat("Garfield",   38, "orange and black")
val cat2 = Cat("Heathcliff", 33, "orange and black")
val optionCat1 = Option(cat1)
val optionCat2 = Option.empty[Cat]

