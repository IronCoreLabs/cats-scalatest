package cats.scalatest

import scala.collection.GenTraversable
import org.scalatest.enablers.Collecting
import cats.data.NonEmptyList

trait NonEmptyListScalaTestInstances {

  /**
   * Support for using .loneElement on NonEmptyList
   * http://www.scalatest.org/user_guide/using_matchers#singleElementCollections
   */
  implicit def collectingNonEmptyList[A]: Collecting[A, NonEmptyList[A]] = new Collecting[A, NonEmptyList[A]] {
    override def loneElementOf(collection: NonEmptyList[A]): Option[A] =
      if (collection.tail.isEmpty) Some(collection.head) else None
    override def sizeOf(collection: NonEmptyList[A]): Int = collection.length
    override def genTraversableFrom(collection: NonEmptyList[A]): GenTraversable[A] = collection.toList
  }

}

/**
 * Companion object for easy importing – rather than mixing in.
 */
object NonEmptyListScalaTestInstances extends NonEmptyListScalaTestInstances
