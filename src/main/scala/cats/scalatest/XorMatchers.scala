package cats.scalatest

import org.scalatest.matchers.{ MatchResult, BeMatcher, Matcher }

import cats.data.Xor
import cats.syntax.either._

trait XorMatchers {
  def beLeft[E](element: E): Matcher[E Xor _] = new BeCatsLeftXor[E](element)

  def left[E]: BeMatcher[E Xor _] = new IsCatsLeftXorMatcher[E]

  def beRight[T](element: T): Matcher[_ Xor T] = new BeCatsRightXorMatcher[T](element)

  def right[T]: BeMatcher[_ Xor T] = new IsCatsRightXorMatcher[T]
}

final private[scalatest] class BeCatsRightXorMatcher[T](element: T) extends Matcher[_ Xor T] {
  def apply(xor: _ Xor T): MatchResult = {
    MatchResult(
      xor.fold(_ => false, _ == element),
      s"$xor did not contain an Xor.Right element matching '$element'.",
      s"$xor contained an Xor.Right element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class BeCatsLeftXor[E](element: E) extends Matcher[E Xor _] {
  def apply(xor: E Xor _): MatchResult = {
    MatchResult(
      xor.fold(_ == element, _ => false),
      s"$xor did not contain an Xor.Left element matching '$element'.",
      s"$xor contained an Xor.Left element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class IsCatsLeftXorMatcher[E] extends BeMatcher[E Xor _] {
  def apply(xor: E Xor _): MatchResult = MatchResult(
    xor.isLeft,
    s"$xor was not an Xor.Left, but should have been.",
    s"$xor was an Xor.Left, but should *NOT* have been."
  )
}

final private[scalatest] class IsCatsRightXorMatcher[T] extends BeMatcher[_ Xor T] {
  def apply(xor: _ Xor T): MatchResult = MatchResult(
    xor.isRight,
    s"$xor was not an Xor.Right, but should have been.",
    s"$xor was an Xor.Right, but should *NOT* have been."
  )
}

