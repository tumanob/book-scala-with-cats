package Articles

class CompanionObject {
private val classValueString = "I belong to Class"
  def printFoo(): Unit = println(
    s"Class Private: $classValueString, Object: ${CompanionObject.objectValueString}"
  )
}

object CompanionObject {
  private val objectValueString = "I belong to object"
  val  test = new CompanionObject

  def getMessage (obj:CompanionObject): String = {
    obj.classValueString
  }

  def printObject(): Unit = println(getMessage(test))
}
