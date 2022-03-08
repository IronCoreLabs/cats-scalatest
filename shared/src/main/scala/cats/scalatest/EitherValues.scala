package cats.scalatest

import org.scalatest.exceptions.{StackDepthException, TestFailedException}

import org.scalactic.source
import scala.util.{Either, Left, Right}

trait EitherValues {
  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a `value` method to `scala.util.Either`
   *
   * @param either
   *   the `scala.util.Either` on which to add the `value` method
   */
  implicit def convertEitherToEitherable[E, T](either: E Either T)(implicit pos: source.Position): Eitherable[E, T] =
    new Eitherable(either, pos)

  /**
   * Container class for matching success type stuff in `scala.util.Either` containers, similar to
   * `org.scalatest.OptionValues.Valuable`
   *
   * Meant to allow you to make statements like:
   *
   * <pre class="stREPL"> result.value should be &gt; 15 </pre>
   *
   * Where it only matches if result is `Valid` and is also greater than 15.
   *
   * Otherwise your test will fail, indicating that it was left instead of right
   *
   * @param either
   *   A `scala.util.Either` object to try converting to a `Eitherable`
   *
   * @see
   *   org.scalatest.OptionValues.Valuable
   */
  final class Eitherable[E, T](either: E Either T, pos: source.Position) {

    /**
     * Extract the `Right` from the Either. If the value is not a right the test will fail.
     */
    def value: T =
      either match {
        case Right(right) => right
        case Left(left) =>
          throw new TestFailedException(
            (_: StackDepthException) => Some(s"'$left' is a Left, expected a Right."),
            None,
            pos
          )
      }

    /**
     * Use .leftValue on an Either to extract the left side. Like .value, but for the left. If the value is a right, the
     * test will fail.
     */
    def leftValue: E =
      either match {
        case Right(right) =>
          throw new TestFailedException(
            (_: StackDepthException) => Some(s"'$right' is a Right, expected a Left."),
            None,
            pos
          )
        case Left(left) => left
      }
  }
}

/**
 * Companion object for easy importing – rather than mixing in – to allow `EitherValues` operations.
 *
 * This will permit you to invoke a `value` method on an instance of a `scala.util.Either`, which attempts to unwrap the
 * `Valid`.
 *
 * Similar to `org.scalatest.OptionValues.Valuable`
 *
 * Meant to allow you to make statements like:
 *
 * <pre class="stREPL"> result.value should be &gt; 15 </pre>
 *
 * Where it only matches if result is both an `Either.Vaild` and has a value > 15.
 *
 * Otherwise your test will fail, indicating that it was an Invalid instead of Valid
 *
 * @see
 *   EitherValues.EitherValuable
 */
object EitherValues extends EitherValues
