package cats.scalatest

import org.scalatest.matchers.{BeMatcher, MatchResult, Matcher}

import scala.util.Either
import cats.syntax.either._

trait EitherMatchers {

  /**
   * Checks to see if `scala.util.Either` is a specific Left element.
   */
  def beLeft[E](element: E): Matcher[Either[E, ?]] = new BeCatsLeftEither[E](element)

  /**
   * Checks to see if `scala.util.Either` is a `Left`.
   */
  def left[E]: BeMatcher[Either[E, ?]] = new IsCatsLeftEitherMatcher[E]

  /**
   * Checks to see if `scala.util.Either` is a specific Right element.
   */
  def beRight[T](element: T): Matcher[Either[?, T]] = new BeCatsRightEitherMatcher[T](element)

  /**
   * Checks to see if `scala.util.Either` is a `Right`.
   */
  def right[T]: BeMatcher[Either[?, T]] = new IsCatsRightEitherMatcher[T]
}

/**
 * Import singleton in case you prefer to import rather than mix in.
 * {{{
 * import EitherMatchers._
 * result should beRight(100)
 * }}}
 */
object EitherMatchers extends EitherMatchers

final private[scalatest] class BeCatsRightEitherMatcher[T](element: T) extends Matcher[Either[?, T]] {
  def apply(either: Either[?, T]): MatchResult =
    MatchResult(
      either.fold(_ => false, _ == element),
      s"'$either' did not contain an Right element matching '$element'.",
      s"'$either' contained an Right element matching '$element', but should not have."
    )
}

final private[scalatest] class BeCatsLeftEither[E](element: E) extends Matcher[Either[E, ?]] {
  def apply(either: Either[E, ?]): MatchResult =
    MatchResult(
      either.fold(_ == element, _ => false),
      s"'$either' did not contain an Left element matching '$element'.",
      s"'$either' contained an Left element matching '$element', but should not have."
    )
}

final private[scalatest] class IsCatsLeftEitherMatcher[E] extends BeMatcher[Either[E, ?]] {
  def apply(either: Either[E, ?]): MatchResult =
    MatchResult(
      either.isLeft,
      s"'$either' was not an Left, but should have been.",
      s"'$either' was an Left, but should *NOT* have been."
    )
}

final private[scalatest] class IsCatsRightEitherMatcher[T] extends BeMatcher[Either[?, T]] {
  def apply(either: Either[?, T]): MatchResult =
    MatchResult(
      either.isRight,
      s"'$either' was not an Right, but should have been.",
      s"'$either' was an Right, but should *NOT* have been."
    )
}
