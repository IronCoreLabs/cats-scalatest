package cats.scalatest

import org.scalatest.matchers.{ BeMatcher, MatchResult, Matcher }

import cats.data.{ Validated, ValidatedNel, NonEmptyList }

trait ValidatedMatchers {

  /**
   * Checks if a `cats.data.ValidatedNel` contains a specific failure element
   * Usage: `
   *   validationObj should haveFailure (someErrorMessageOrObject)
   * `
   * Can also be used to test multiple elements: `
   *  validationObj should (haveFailure (someErrorMessageOrObject) and
   *                       haveFailure (someOtherErrorMessageOrObject))
   * `
   *
   */
  def haveFailure[E](element: E): Matcher[ValidatedNel[E, _]] = new HasCatsValidatedFailure[E](element)

  /**
   * Checks if a `cats.data.Validated` is a specific failure element.
   */
  def beFailure[E](element: E): Matcher[Validated[E, _]] = new BeCatsInvalidMatcher[E](element)

  /**
   * Checks to see if the `cats.data.Validated` is a specific failure element.
   */
  def failure[E]: BeMatcher[Validated[E, _]] = new IsCatsInvalidMatcher[E]

  /**
   * Checks if a `cats.data.Validated` is an instance of Valid.
   */
  def success[T]: BeMatcher[Validated[_, T]] = new IsCatsValidMatcher[T]

  /**
   * Checks if a `cats.data.Validated` is a specific success element.
   */
  def beSuccess[T](element: T): Matcher[Validated[_, T]] = new BeValidMatcher[T](element)
}

/**
 * Import singleton in case you prefer to import rather than mix in
 */
final object ValidatedMatchers extends ValidatedMatchers

//Classes used above
final private[scalatest] class HasCatsValidatedFailure[E](element: E) extends Matcher[ValidatedNel[E, _]] {
  def apply(validated: ValidatedNel[E, _]): MatchResult = {
    MatchResult(
      validated.fold(n => (n.head :: n.tail).contains(element), _ => false),
      s"$validated did not contain an Invalid element matching '$element'.",
      s"$validated contained an Invalid element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class BeCatsInvalidMatcher[E](element: E) extends Matcher[Validated[E, _]] {
  def apply(validated: Validated[E, _]): MatchResult = {
    MatchResult(
      validated.fold(_ == element, _ => false),
      s"$validated did not contain an Invalid element matching '$element'.",
      s"$validated contained an Invalid element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class BeValidMatcher[T](element: T) extends Matcher[Validated[_, T]] {
  def apply(validated: Validated[_, T]): MatchResult = {
    MatchResult(
      validated.fold(_ => false, _ == element),
      s"$validated did not contain a Valid element matching '$element'.",
      s"$validated contained a Valid element matching '$element', but should not have."
    )
  }
}

final private[scalatest] class IsCatsValidMatcher[T] extends BeMatcher[Validated[_, T]] {
  def apply(validated: Validated[_, T]): MatchResult = MatchResult(
    validated.isValid,
    s"$validated was not Valid, but should have been.",
    s"$validated was Valid, but should not have been."
  )
}

final private[scalatest] class IsCatsInvalidMatcher[E] extends BeMatcher[Validated[E, _]] {
  def apply(validated: Validated[E, _]): MatchResult = MatchResult(
    validated.isInvalid,
    s"$validated was not an Invalid, but should have been.",
    s"$validated was an Invalid, but should not have been."
  )
}

