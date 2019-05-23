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
      if (isJVM) {
        caught.failedCodeLineNumber.value should equal(thisLineNumber - 3)
      }
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }

  "invalidValue on Validated" should {
    "return the value if it's invalid" in {
      val r = Validated.Invalid(thisRecord)
      r.invalidValue should ===(thisRecord)
    }

    "throw TestFailedException if the Validated is Valid" in {
      val r = Validated.Valid(thisRecord)
      val caught = intercept[TestFailedException] {
        r.invalidValue
      }
      if (isJVM) {
        caught.failedCodeLineNumber.value should equal(thisLineNumber - 3)
      }
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }

  "valid on Validated" should {
    "return the valid if it's a Valid" in {
      val r = Validated.Valid(thisRecord)
      r.valid should ===(r)
    }

    "throw TestFailedException if the Validated is Invalid" in {
      val r: String Validated String = Validated.Invalid(thisTobacconist)
      val caught =
        intercept[TestFailedException] {
          r.valid should ===(r)
        }
      if (isJVM) {
        caught.failedCodeLineNumber.value should equal(thisLineNumber - 3)
      }
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }

  "invalid on Validated" should {
    "return the invalid if it's a Invalid" in {
      val r = Validated.Invalid(thisTobacconist)
      r.invalid should ===(r)
    }

    "throw TestFailedException if the Validated is Valid" in {
      val r: String Validated String = Validated.Valid(thisRecord)
      val caught =
        intercept[TestFailedException] {
          r.invalid should ===(r)
        }
      if (isJVM) {
        caught.failedCodeLineNumber.value should equal(thisLineNumber - 3)
      }
      caught.failedCodeFileName.value should be("ValidatedValuesSpec.scala")
    }
  }
}

