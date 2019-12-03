package cats.scalatest

import org.scalatest.exceptions.{StackDepthException, TestFailedException}

import cats.data.Validated
import Validated.{Invalid, Valid}
import org.scalactic.source

trait ValidatedValues {
  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a `value` method to `cats.data.Validated`
   *
   * @param validated the `cats.data.Validated` on which to add the `value` method
   */
  implicit def convertValidatedToValidatable[E, T](
    validated: Validated[E, T]
  )(implicit pos: source.Position): Validatable[E, T] =
    new Validatable(validated, pos)

  /**
   * Container class for matching success
   * type stuff in `cats.data.Validated` containers,
   * similar to `org.scalatest.OptionValues.Valuable`
   *
   * Meant to allow you to make statements like:
   *
   * <pre class="stREPL">
   *   result.value should be &gt; 15
   *   result.valid.value should be(Valid(15))
   * </pre>
   *
   * Where it only matches if result is `Valid(9)`
   *
   * Otherwise your test will fail, indicating that it was left instead of right
   *
   * @param validated A `cats.data.Validated` object to try converting to a `Validatable`
   *
   * @see org.scalatest.OptionValues.Valuable
   */
  final class Validatable[E, T](validated: Validated[E, T], pos: source.Position) {
    def value: T = validated match {
      case Valid(valid) => valid
      case Invalid(left) =>
        throw new TestFailedException(
          (_: StackDepthException) => Some(s"'$left' is Invalid, expected Valid."),
          None,
          pos
        )
    }

    /**
     * Allow .invalidValue on an validated to extract the invalid side. Like .value, but for the `Invalid`.
     */
    def invalidValue: E = validated match {
      case Valid(valid) =>
        throw new TestFailedException(
          (_: StackDepthException) => Some(s"'$valid' is Valid, expected Invalid."),
          None,
          pos
        )
      case Invalid(left) => left
    }

    /**
     * Returns the <code>Validated</code> passed to the constructor as a <code>Valid</code>, if it is a <code>Valid</code>,
     * else throws <code>TestFailedException</code> with a detail message indicating the <code>Validated</code> was not a <code>Valid</code>.
     */
    def valid: Valid[T] = validated match {
      case valid: Valid[T] => valid
      case _ =>
        throw new TestFailedException(
          (_: StackDepthException) => Some("The Validated on which valid was invoked was not a Valid."),
          None,
          pos
        )
    }

    /**
     * Returns the <code>Validated</code> passed to the constructor as an <code>Invalid</code>, if it is an <code>Invalid</code>,
     * else throws <code>TestFailedException</code> with a detail message indicating the <code>Validated</code> was not an <code>Invalid</code>.
     */
    def invalid: Invalid[E] = validated match {
      case invalid: Invalid[E] => invalid
      case _ =>
        throw new TestFailedException(
          (_: StackDepthException) => Some("The Validated on which invalid was invoked was not an Invalid."),
          None,
          pos
        )
    }
  }
}

/**
 *
 * Companion object for easy importing – rather than mixing in – to allow `ValidatedValues` operations.
 *
 * This will permit you to invoke a `value` method on an instance of a `cats.data.Validated`,
 * which attempts to unwrap the Validated.Valid
 *
 * Similar to `org.scalatest.OptionValues.Valuable`
 *
 * Meant to allow you to make statements like:
 *
 * <pre class="stREPL">
 *   result.value should be &gt; 15
 * </pre>
 *
 * Where it only matches if result is both valid and greater than 15.
 *
 * Otherwise your test will fail, indicating that it was an Invalid instead of Valid
 *
 * @see org.scalatest.OptionValues.Valuable
 */
object ValidatedValues extends ValidatedValues
