package cats.scalatest

import org.scalatest.exceptions.TestFailedException

import cats.data.Validated
import Validated.{ Valid, Invalid }

trait ValidatedValues {
  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a `value` method to `cats.data.Validated`
   *
   * @param validated the `cats.data.Validated` on which to add the `value` method
   */
  implicit def convertValidatedToValidatable[E, T](validated: Validated[E, T]): Validatable[E, T] =
    new Validatable(validated)

  /**
   * Container class for matching success
   * type stuff in `cats.data.Validated` containers,
   * similar to `org.scalatest.OptionValues.Valuable`
   *
   * Meant to allow you to make statements like:
   *
   * <pre class="stREPL">
   *   result.value should be &gt; 15
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
  final class Validatable[E, T](validated: Validated[E, T]) {
    def value: T = validated match {
      case Valid(valid) => valid
      case Invalid(left) =>
        throw new TestFailedException(sde => Some(s"'$left' is Invalid, expected Valid."), None,
          StackDepthHelpers.getStackDepthFun("ValidatedValues.scala", "value"))
    }

    /**
     * Allow .invalidValue on an validated to extract the invalid side. Like .value, but for the `Invalid`.
     */
    def invalidValue: E = validated match {
      case Valid(valid) =>
        throw new TestFailedException(sde => Some(s"'$valid' is Valid, expected Invalid."), None,
          StackDepthHelpers.getStackDepthFun("ValidatedValues.scala", "invalidValue"))
      case Invalid(left) => left
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
