package cats.scalatest

import org.scalatest.exceptions.TestFailedException
import cats.data.Validated

class ValidatedValuesSpec extends TestBase {
  import ValidatedValues._

  "value on Validated" should {
    "return the value inside a Validated.Right if that Validated is Validated.Right" in {
      val r: String Validated String = Validated.Valid(thisRecord)
      r.value should ===(thisRecord)
    }

    "should throw TestFailedException if that Validated is a left " in {
      val r: String Validated String = Validated.Invalid(thisTobacconist)
      val caught =
        intercept[TestFailedException] {
          r.value should ===(thisRecord)
        }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }

  "leftValue on Validated" should {
    "return the value if it's left" in {
      val r = Validated.Invalid(thisRecord)
      r.leftValue should ===(thisRecord)
    }

    "throw TestFailedException if the Validated is right" in {
      val r = Validated.Valid(thisRecord)
      val caught = intercept[TestFailedException] {
        r.leftValue
      }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }
}

