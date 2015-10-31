package cats.scalatest

import org.scalatest.matchers.{ MatchResult, BeMatcher, Matcher }

import cats.data.Xor
import cats.syntax.either._

trait XorMatchers {
  /**
   * Checks to see if `cats.data.Xor` is a specific Left element.
   */
  def beLeft[E](element: E): Matcher[E Xor _] = new BeCatsLeftXor[E](element)

  /**
   * Checks to see if `cats.data.Xor` is a `Left`.
   */
  def left[E]: BeMatcher[E Xor _] = new IsCatsLeftXorMatcher[E]

  /**
   * Checks to see if `cats.data.Xor` is a specific Right element.
   */
  def beRight[T](element: T): Matcher[_ Xor T] = new BeCatsRightXorMatcher[T](element)

  /**
   * Checks to see if `cats.data.Xor` is a `Right`.
   */
  def right[T]: BeMatcher[_ Xor T] = new IsCatsRightXorMatcher[T]
}

/**
 * Import singleton in case you prefer to import rather than mix in.
 * {{{
 * import XorMatchers._
 * result should beRight(100)
 * }}}
 */
final object XorMatchers extends XorMatchers

final private[scalatest] class BeCatsRightXorMatcher[T](element: T) extends Matcher[_ Xor T] {
  def apply(xor: _ Xor T): MatchResult = {
    MatchResult(
      xor.fold(_ => false, _ == element),
      s"'$xor' did not contain an Xor.Right element matching '$element'.",
      s"'$xor' contained an Xor.Right element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class BeCatsLeftXor[E](element: E) extends Matcher[E Xor _] {
  def apply(xor: E Xor _): MatchResult = {
    MatchResult(
      xor.fold(_ == element, _ => false),
      s"'$xor' did not contain an Xor.Left element matching '$element'.",
      s"'$xor' contained an Xor.Left element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class IsCatsLeftXorMatcher[E] extends BeMatcher[E Xor _] {
  def apply(xor: E Xor _): MatchResult = MatchResult(
    xor.isLeft,
    s"'$xor' was not an Xor.Left, but should have been.",
    s"'$xor' was an Xor.Left, but should *NOT* have been."
  )
}

final private[scalatest] class IsCatsRightXorMatcher[T] extends BeMatcher[_ Xor T] {
  def apply(xor: _ Xor T): MatchResult = MatchResult(
    xor.isRight,
    s"'$xor' was not an Xor.Right, but should have been.",
    s"'$xor' was an Xor.Right, but should *NOT* have been."
  )
}

