package cats.scalatest

import cats.data.{NonEmptyList => NEL, Validated, ValidatedNel}
import org.scalatest.matchers.{BeMatcher, MatchResult, Matcher}
import shapeless3.typeable.Typeable
import shapeless3.typeable.syntax.typeable.cast

trait ValidatedMatchers {

  /**
   * Checks if a `cats.data.ValidatedNel` contains a specific failure element Usage:
   * {{{
   *   validationObj should haveInvalid (someErrorMessageOrObject)
   * }}}
   * Can also be used to test multiple elements: `
   * {{{
   *  validationObj should (haveInvalid (someErrorMessageOrObject) and
   *                       haveInvalid (someOtherErrorMessageOrObject))
   * }}}
   */
  def haveInvalid[E](element: E): Matcher[ValidatedNel[E, ?]] = new HasCatsValidatedFailure[E](element)

  /**
   * Checks if a `cats.data.ValidatedNel` contains a failure element matching a specific type Usage:
   * {{{
   *   validationObj should haveAnInvalid[someErrorType]
   * }}}
   * Can also be used to test multiple elements: `
   * {{{
   *  validationObj should (haveAnInvalid[someErrorType] and
   *                       haveAnInvalid[someOtherErrorType])
   * }}}
   */
  def haveAnInvalid[E: Typeable]: Matcher[ValidatedNel[Any, ?]] = new HasACatsValidatedFailure[E]

  /**
   * Checks if a `cats.data.Validated` is a specific `Invalid` element.
   */
  def beInvalid[E](element: E): Matcher[Validated[E, ?]] = new BeCatsInvalidMatcher[E](element)

  /**
   * Checks if the `cats.data.Validated` is an `Invalid`.
   */
  def invalid[E]: BeMatcher[Validated[E, ?]] = new IsCatsInvalidMatcher[E]

  /**
   * Checks if a `cats.data.Validated` is a `Valid`.
   */
  def valid[T]: BeMatcher[Validated[?, T]] = new IsCatsValidMatcher[T]

  /**
   * Checks if a `cats.data.Validated` is an instance of `Valid`.
   */
  def beValid[T](element: T): Matcher[Validated[?, T]] = new BeValidMatcher[T](element)
}

/**
 * Import singleton in case you prefer to import rather than mix in.
 * {{{
 * import ValidatedMatchers._
 * result should beValid (100)
 * }}}
 */
object ValidatedMatchers extends ValidatedMatchers

//Classes used above
final private[scalatest] class HasCatsValidatedFailure[E](element: E) extends Matcher[ValidatedNel[E, ?]] {
  def apply(validated: ValidatedNel[E, ?]): MatchResult =
    MatchResult(
      validated.fold(n => (n.head :: n.tail).contains(element), ? => false),
      s"'$validated' did not contain an Invalid element matching '$element'.",
      s"'$validated' contained an Invalid element matching '$element', but should not have."
    )
}

final private[scalatest] class HasACatsValidatedFailure[+T: Typeable] extends Matcher[ValidatedNel[Any, ?]] {
  def apply(validated: ValidatedNel[Any, ?]): MatchResult = {
    val expected: String = Typeable[T].describe

    MatchResult(
      validated.fold(
        (n: NEL[_]) =>
          (n.head :: n.tail).exists { e =>
            e.cast[T].isDefined
          },
        _ => false
      ),
      s"'$validated' did not contain an Invalid element matching '$expected'.",
      s"'$validated' contained an Invalid element matching '$expected' but " +
        s"should not have."
    )
  }
}

final private[scalatest] class BeCatsInvalidMatcher[E](element: E) extends Matcher[Validated[E, ?]] {
  def apply(validated: Validated[E, ?]): MatchResult =
    MatchResult(
      validated.fold(_ == element, ? => false),
      s"'$validated' did not contain an Invalid element matching '$element'.",
      s"'$validated' contained an Invalid element matching '$element', but should not have."
    )
}

final private[scalatest] class BeValidMatcher[T](element: T) extends Matcher[Validated[?, T]] {
  def apply(validated: Validated[?, T]): MatchResult =
    MatchResult(
      validated.fold(_ => false, _ == element),
      s"'$validated' did not contain a Valid element matching '$element'.",
      s"'$validated' contained a Valid element matching '$element', but should not have."
    )
}

final private[scalatest] class IsCatsValidMatcher[T] extends BeMatcher[Validated[?, T]] {
  def apply(validated: Validated[?, T]): MatchResult =
    MatchResult(
      validated.isValid,
      s"'$validated' was not Valid, but should have been.",
      s"'$validated' was Valid, but should not have been."
    )
}

final private[scalatest] class IsCatsInvalidMatcher[E] extends BeMatcher[Validated[E, ?]] {
  def apply(validated: Validated[E, ?]): MatchResult =
    MatchResult(
      validated.isInvalid,
      s"'$validated' was not an Invalid, but should have been.",
      s"'$validated' was an Invalid, but should not have been."
    )
}
