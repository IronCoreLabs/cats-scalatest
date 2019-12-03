package cats.scalatest

import scala.util.{Left, Right}

class EitherMatchersSpec extends TestBase with EitherMatchers {
  val goodHovercraft = Right(hovercraft)
  val badTobacconist = Left(thisTobacconist)
  val badRecord = Left(thisRecord)

  "EitherMatchers" should {
    "Match 'blind' invalid (i.e. not with specific element)" in {
      badTobacconist should be(left)
    }
    "Match 'valued' left disjunction syntax" in {
      badTobacconist should beLeft(thisTobacconist)
    }
    "Match 'valued' right disjunction syntax" in {
      goodHovercraft should beRight(hovercraft)
    }
    "Match 'blind' right disjunction syntax (i.e. with no specific element)" in {
      goodHovercraft should be(right)
    }
    "Match negation of left when it's right" in {
      goodHovercraft should not be left
    }
    "Match negation of right when it's left" in {
      badRecord should not be right
    }
  }
}
