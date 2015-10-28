package cats.scalatest

import org.scalatest.exceptions.TestFailedException

import cats.data.Xor
import cats.syntax.xor._

trait XorValues {
  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a `value` method to `scalaz.\/`
   *
   * @param xor the `scalaz.\/` on which to add the `value` method
   */
  implicit def convertXorToXorable[E, T](xor: E Xor T): Xorable[E, T] = new Xorable(xor)
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
 * Where it only matches if result is `Valid(9)`
 *
 * Otherwise your test will fail, indicating that it was an Invalid instead of Valid
 *
 * @see org.scalatest.OptionValues.Valuable
 */
object XorValues extends XorValues

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
 * Where it only matches if result is `Valid(9)`
 *
 * Otherwise your test will fail, indicating that it was left instead of right
 *
 * @param xor A `cats.data.Xor` object to try converting to a `Xorable`
 *
 * @see org.scalatest.OptionValues.Valuable
 */
final class Xorable[E, T](xor: E Xor T) {
  def value: T = {
    xor match {
      case Xor.Right(right) => right
      case Xor.Left(left) =>
        throw new TestFailedException(sde => Some(s"$left is an Xor.Left, expected an Xor.Right."), None,
          StackDepthHelpers.getStackDepthFun("XorValues.scala", "value"))
    }
  }

  /**
   * Allow .leftValue on an Xor to extract the left side. Like .value, but for the left.
   */
  def leftValue: E = {
    xor match {
      case Xor.Right(right) =>
        throw new TestFailedException(sde => Some(s"$right is Valid, expected Invalid."), None,
          StackDepthHelpers.getStackDepthFun("XorValues.scala", "leftValue"))
      case Xor.Left(left) => left
    }
  }
}
