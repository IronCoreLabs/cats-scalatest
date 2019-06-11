package cats.scalatest

import org.scalatest.matchers.{ MatchResult, BeMatcher, Matcher }

import scala.util.Either
import cats.syntax.either._

trait EitherMatchers {
  /**
   * Checks to see if `scala.util.Either` is a specific Left element.
   */
  def beLeft[E](element: E): Matcher[E Either _] = new BeCatsLeftEither[E](element)

  /**
   * Checks to see if `scala.util.Either` is a `Left`.
   */
  def left[E]: BeMatcher[E Either _] = new IsCatsLeftEitherMatcher[E]

  /**
   * Checks to see if `scala.util.Either` is a specific Right element.
   */
  def beRight[T](element: T): Matcher[_ Either T] = new BeCatsRightEitherMatcher[T](element)

  /**
   * Checks to see if `scala.util.Either` is a `Right`.
   */
  def right[T]: BeMatcher[_ Either T] = new IsCatsRightEitherMatcher[T]
}

/**
 * Import singleton in case you prefer to import rather than mix in.
 * {{{
 * import EitherMatchers._
 * result should beRight(100)
 * }}}
 */
final object EitherMatchers extends EitherMatchers

final private[scalatest] class BeCatsRightEitherMatcher[T](element: T) extends Matcher[_ Either T] {
  def apply(either: _ Either T): MatchResult = {
    MatchResult(
      either.fold(_ => false, _ == element),
      s"'$either' did not contain an Right element matching '$element'.",
      s"'$either' contained an Right element matching '$element', but should not have.")
  }
}

final private[scalatest] class BeCatsLeftEither[E](element: E) extends Matcher[E Either _] {
  def apply(either: E Either _): MatchResult = {
    MatchResult(
      either.fold(_ == element, _ => false),
      s"'$either' did not contain an Left element matching '$element'.",
      s"'$either' contained an Left element matching '$element', but should not have.")
  }
}

final private[scalatest] class IsCatsLeftEitherMatcher[E] extends BeMatcher[E Either _] {
  def apply(either: E Either _): MatchResult = MatchResult(
    either.isLeft,
    s"'$either' was not an Left, but should have been.",
    s"'$either' was an Left, but should *NOT* have been.")
}

final private[scalatest] class IsCatsRightEitherMatcher[T] extends BeMatcher[_ Either T] {
  def apply(either: _ Either T): MatchResult = MatchResult(
    either.isRight,
    s"'$either' was not an Right, but should have been.",
    s"'$either' was an Right, but should *NOT* have been.")
}

