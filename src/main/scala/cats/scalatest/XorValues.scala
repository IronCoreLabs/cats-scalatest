package cats.scalatest

import org.scalatest.exceptions.{ TestFailedException, StackDepthException }

import cats.data.Xor
import cats.syntax.xor._
import org.scalactic.source

trait XorValues {
  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a `value` method to `cats.data.Xor`
   *
   * @param xor the `cats.data.Xor` on which to add the `value` method
   */
  implicit def convertXorToXorable[E, T](xor: E Xor T)(implicit pos: source.Position): Xorable[E, T] = new Xorable(xor, pos)

  /**
   * Container class for matching success
   * type stuff in `cats.data.Xor` containers,
   * similar to `org.scalatest.OptionValues.Valuable`
   *
   * Meant to allow you to make statements like:
   *
   * <pre class="stREPL">
   *   result.value should be &gt; 15
   * </pre>
   *
   * Where it only matches if result is `Valid` and is also greater than 15.
   *
   * Otherwise your test will fail, indicating that it was left instead of right
   *
   * @param xor A `cats.data.Xor` object to try converting to a `Xorable`
   *
   * @see org.scalatest.OptionValues.Valuable
   */
  final class Xorable[E, T](xor: E Xor T, pos: source.Position) {
    /**
     * Extract the `Xor.Right` from the Xor. If the value is not a right the test will fail.
     */
    def value: T = {
      xor match {
        case Xor.Right(right) => right
        case Xor.Left(left) =>
          throw new TestFailedException((_: StackDepthException) => Some(s"'$left' is an Xor.Left, expected an Xor.Right."), None, pos)
      }
    }

    /**
     * Use .leftValue on an Xor to extract the left side. Like .value, but for the left.
     * If the value is a right, the test will fail.
     */
    def leftValue: E = {
      xor match {
        case Xor.Right(right) =>
          throw new TestFailedException((_: StackDepthException) => Some(s"'$right' is Valid, expected Invalid."), None, pos)
        case Xor.Left(left) => left
      }
    }
  }
}

/**
 *
 * Companion object for easy importing – rather than mixing in – to allow `XorValues` operations.
 *
 * This will permit you to invoke a `value` method on an instance of a `cats.data.Xor`,
 * which attempts to unwrap the `Valid`.
 *
 * Similar to `org.scalatest.OptionValues.Valuable`
 *
 * Meant to allow you to make statements like:
 *
 * <pre class="stREPL">
 *   result.value should be &gt; 15
 * </pre>
 *
 * Where it only matches if result is both an `Xor.Vaild` and has a value > 15.
 *
 * Otherwise your test will fail, indicating that it was an Invalid instead of Valid
 *
 * @see XorValues.XorValuable
 */
object XorValues extends XorValues
