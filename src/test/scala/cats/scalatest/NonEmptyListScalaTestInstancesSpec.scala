package cats.scalatest

import org.scalatest.exceptions.TestFailedException
import org.scalatest.enablers.Collecting
import org.scalatest.LoneElement._
import org.scalatest.Inspectors._
import cats.data.NonEmptyList

class NonEmptyListScalaTestInstancesSpec extends TestBase {
  import NonEmptyListScalaTestInstances._

  "loneElement" should {
    "apply an assertion when there is a single element" in {
      val nel: NonEmptyList[Int] = NonEmptyList.one(10)
      nel.loneElement should be <= 10
    }

    "should throw TestFailedException if the NonEmptyList has more elements" in {
      val nel: NonEmptyList[Int] = NonEmptyList.of(10, 16)
      val caught =
        intercept[TestFailedException] {
          nel.loneElement should ===(thisRecord)
        }
      if (isJVM) {
        caught.failedCodeLineNumber.value should equal(thisLineNumber - 3)
      }
      caught.failedCodeFileName.value should be("NonEmptyListScalaTestInstancesSpec.scala")
    }
  }

  "inspectors" should {
    "state something about all elements" in {
      val nel: NonEmptyList[Int] = NonEmptyList.of(1, 2, 3, 4, 5)
      forAll(nel) { _ should be > 0 }
    }
  }

  "sizeOf" should {
    "return the size of the collection" in {
      val nel: NonEmptyList[Int] = NonEmptyList.of(1, 2)
      implicitly[Collecting[Int, NonEmptyList[Int]]].sizeOf(nel) shouldBe 2
    }
  }
}
