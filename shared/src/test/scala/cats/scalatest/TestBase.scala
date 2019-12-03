package cats.scalatest

import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

abstract class TestBase extends AnyWordSpec with Matchers with OptionValues {
  val thisRecord = "I will not buy this record, it is scratched."
  val thisTobacconist = "Ah! I will not buy this tobacconist's, it is scratched."
  val hovercraft = "Yes, cigarettes. My hovercraft is full of eels."

  // As advised by @sjrd: https://gitter.im/scala-js/scala-js?at=5ce51e9b9d64e537bcef6f08
  final val isJS = 1.0.toString() == "1"
  final val isJVM = !isJS

  /**
   * Shamelessly swiped from Scalatest.
   */
  final def thisLineNumber: Int = {
    val st = Thread.currentThread.getStackTrace

    if (!st(2).getMethodName.contains("thisLineNumber"))
      st(2).getLineNumber
    else
      st(3).getLineNumber
  }
}
