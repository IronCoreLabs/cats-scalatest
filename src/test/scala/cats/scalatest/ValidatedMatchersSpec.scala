package cats.scalatest

import cats.data.{NonEmptyList, ValidatedNel}
import cats.data.Validated.{Invalid, Valid}

class ValidatedMatchersSpec extends TestBase with ValidatedMatchers {
  "ValidatedMatchers" should {
    val simpleFailureNel: ValidatedNel[String, Nothing] = Invalid(NonEmptyList.of(thisRecord, thisTobacconist))

    "Match one specific element in an Invalid NEL" in {
      simpleFailureNel should haveInvalid(thisRecord)
    }

    "Match multiple specific elements in an Invalid NEL" in {
      simpleFailureNel should (haveInvalid(thisRecord).and(haveInvalid(thisTobacconist)))
    }

    "Match a specific element of a single Invalid" in {
      Invalid(thisRecord) should beInvalid(thisRecord)
    }

    "Test whether a Validated instance is a Invalid w/o specific element value" in {
      Invalid(thisTobacconist) should be(invalid)
    }

    "By negating 'invalid', test whether a Validated instance is a Valid" in {
      Valid(hovercraft) should not be (invalid)
    }

    "Test whether a Validated instance is a Valid" in {
      Valid(hovercraft) should be(valid)
    }

    "By negating 'valid', test whether a Validated instance is an invalid" in {
      Invalid(thisTobacconist) should not be (valid)
    }

    "Match a specific element of a single Valid" in {
      Valid(hovercraft) should beValid(hovercraft)
    }
  }
}
