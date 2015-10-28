package cats.scalatest

import org.scalatest.{ Matchers, FlatSpec }

import cats.data.{ Validated, ValidatedNel, NonEmptyList }
import cats.data.Validated.{ Valid, Invalid }

class ValidatedMatchersSpec extends TestBase with ValidatedMatchers {

  "ValidatedMatchers" should {
    val simpleFailureNel: ValidatedNel[String, Nothing] = Invalid(NonEmptyList(thisRecord, thisTobacconist))

    "Match one specific element in a Validation failure NEL" in {
      simpleFailureNel should haveFailure(thisRecord)
    }

    "Match multiple specific elements in a Validation failure NEL" in {
      simpleFailureNel should (haveFailure(thisRecord) and
        haveFailure(thisTobacconist))
    }

    "Match a specific element of a single Validation failure" in {
      Invalid(thisRecord) should beFailure(thisRecord)
    }

    "Test whether a Validation instance is a failure w/o specific element value" in {
      Invalid(thisTobacconist) should be(failure)
    }

    "By negating 'failure', test whether a Validation instance is a success w/o specific element value" in {
      Valid(hovercraft) should not be (failure)
    }

    "Test whether a Validation instance is a success w/o specific element value" in {
      Valid(hovercraft) should be(success)
    }

    "By negating 'success', test whether a Validation instance is a failure w/o specific element value" in {
      Invalid(thisTobacconist) should not be (success)
    }

    "Match a specific element of a single Validation success" in {
      Valid(hovercraft) should beSuccess(hovercraft)
    }
  }
}
