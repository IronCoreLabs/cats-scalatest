package cats.scalatest

import org.scalatest.{ FunSpec, Matchers }
import Matchers._
import org.scalatest.OptionValues._
import org.scalatest.exceptions.TestFailedException
import cats.data.Xor

class XorValuesSpec extends TestBase {
  import XorValues._

  "value on Xor" should {
    "return the value inside a Xor.Right if that Xor is Xor.Right" in {
      val r: String Xor String = Xor.Right(thisRecord)
      r.value should ===(thisRecord)
    }

    "should throw TestFailedException if that Xor is a left " in {
      val r: String Xor String = Xor.Left(thisTobacconist)
      val caught =
        intercept[TestFailedException] {
          r.value should ===(thisRecord)
        }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("XorValuesSpec.scala")
    }
  }

  "leftValue on Xor" should {
    "return the value if it's left" in {
      val r = Xor.Left(thisRecord)
      r.leftValue should ===(thisRecord)
    }

    "throw TestFailedException if the Xor is right" in {
      val r = Xor.Right(thisRecord)
      val caught = intercept[TestFailedException] {
        r.leftValue
      }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("XorValuesSpec.scala")
    }
  }
}

