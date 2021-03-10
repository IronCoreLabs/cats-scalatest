# cats-scalatest

[![CI](https://github.com/IronCoreLabs/cats-scalatest/actions/workflows/ci.yml/badge.svg)](https://github.com/IronCoreLabs/cats-scalatest/actions/workflows/ci.yml)
[![codecov.io](https://codecov.io/github/IronCoreLabs/cats-scalatest/coverage.svg?branch=main)](https://codecov.io/github/IronCoreLabs/cats-scalatest?branch=main)
[![scaladoc](https://javadoc-badge.appspot.com/com.ironcorelabs/cats-scalatest_2.11.svg?label=scaladoc)](https://javadoc-badge.appspot.com/com.ironcorelabs/cats-scalatest_2.11)

Scalatest bindings for cats. Inspired by scalaz-scalatest.

Apache 2.0 licensed.

**cats-scalatest** is a [Typelevel](http://typelevel.org/) project. This means we embrace pure, typeful, functional programming, and provide a safe and friendly environment for teaching, learning, and contributing as described in the [Scala Code of Conduct](http://typelevel.org/code-of-conduct.html).

## Setup

We currently crossbuild for Scala 2.12 and 2.13. Prior to 3.0.4 we cross built for 2.11 as well.

Because cats is such a young project the versioning is not quite following the semantic versioning guidelines (yet). Use the below table to understand
which version of the cats-scalatest library you need.

| Cats-Scalatest Version | Cats Version | Scalatest Version |
| ---------------------- | ------------ | ----------------- |
| 1.0.1                  | 0.2.0        | 2.2.4             |
| 1.1.0                  | 0.4.0        | 2.2.4             |
| 1.1.2                  | 0.4.1        | 2.2.4             |
| 1.3.0                  | 0.6.{0,1}    | 2.2.6             |
| 1.4.0                  | 0.7.0        | 2.2.6             |
| 1.5.0                  | 0.7.2        | 2.2.6             |
| 2.0.0                  | 0.7.2        | 3.0.0             |
| 2.1.0                  | 0.8.0        | 3.0.0             |
| 2.1.1                  | 0.8.1        | 3.0.0             |
| 2.2.0                  | 0.9.0        | 3.0.0             |
| 2.3.0                  | 1.0.0-MF     | 3.0.0             |
| 2.3.1                  | 1.0.1        | 3.0.0             |
| 2.4.0                  | 1.5.0        | 3.0.5             |
| 3.0.0                  | 2.0.0        | 3.0.8             |
| 3.0.4                  | 2.0.0        | 3.1.0             |
| 3.0.5                  | 2.1.0        | 3.1.0             |
| 3.1.1                  | 2.1.1        | 3.2.3             |

To include this in your project, add the dependency:

```
//For cats 2.1.0 and scalatest 3.1, see above chart for others.
libraryDependencies += "com.ironcorelabs" %% "cats-scalatest" % "3.0.5" % "test"
```

## What does this provide?

Matchers & Helpers are presently offered for testing of the following cats concepts:

- `Either`
- `Validated`

## Usage

There are two ways to use the provided matchers:

You can mix them in:

```scala
class MySillyWalkSpec extends FlatSpec with Matchers with EitherMatchers {
  // ...
}
```

This makes the matchers in `EitherMatchers` available inside the scope of your test.

You can also import explicitly from a provided object:

```scala
import cats.scalatest.EitherMatchers

class MySillyWalkSpec extends FlatSpec with Matchers {
  import EitherMatchers._
  // ...
}

```

Also brings the matchers into scope.

And now, the matchers themselves.

## Either Matchers

EitherMatchers supplies the following methods:

```
beLeft[E](element: E)
left[E]
beRight[T](element: T)
right[T]
```

### Specific Element Matchers

The matchers that begin with a be prefix are for matching a specific element inside of the `Either`.

Something like the following:

```
val s = "Hello World"
val valueInRight = Right(s)

//This passes
valueInRight should beRight(s)

//This fails with the following message:
//Right(Hello World) did not contain an Right element matching 'goodbye'.
valueInRight should beRight("goodbye")
```

The matchers work the same for `beLeft`.

### Right and Left Matchers

The `left` and `right` matchers are for checking to see if the `Either` is a right or left without caring what's inside.

```
  //This passes
  Left("uh oh") should be(left)

  //This fails with the following message:
  //Left(uh oh) was not an Right, but should have been.
  Left("uh oh") should be(right)
```

## Validated Matchers

cats.data.Validated also has matchers similar to the ones described above.

```
def beInvalid[E](element: E)
def invalid[E]
def valid[T]
def beValid[T](element: T)
```

I won't repeat how they're used here. `Validated` does have some additional
matchers though which allows you to describe values that are in the `Invalid` if
you're using `ValidatedNel`.

The first matcher is `haveInvalid` and can be used like this:

```
val validatedNelValue: ValidatedNel[String, Int] = Invalid(NonEmptyList("error1", "error2"))

//The following works fine:
validatedNelValue should haveInvalid("error1")

//But you can also combine them with the and word to match multiple values:
validateNelValue should (haveInvalid("error1") and haveInvalid("error2"))
```

The second matcher is `haveAnInvalid` and can be used like this:

```
val validatedNelValue: ValidatedNel[Exception, Int] = Invalid(NonEmptyList(new ArrayIndexOutOfBoundsException, new NoSuchElementException))

//The following works fine:
validatedNelValue should haveAnInvalid[NoSuchElementException]
validatedNelValue shouldNot haveAnInvalid[NumberFormatException]

//But you can also combine them with the and word to match multiple values:
validateNelValue should (haveAnInvalid[ArrayIndexOutOfBoundsException] and haveAnInvalid[NoSuchElementException])
```

## Values Helpers

A very common test idiom is to want to assert the Either is a Left or a Right and then extract the value. For this
we supply `EitherValues`. This can be mixed into your test or imported as an object just like the matchers above, but
instead of providing Matchers it instead adds `value` and `leftValue` as syntax to the `Either` type.

```
val x = Right("hello")
//Passes!
x.value shouldBe "hello"

//Fails with the following message:
//    'Hello' is Right, expected Left.
x.leftValue shouldBe "hello"
```

The same is true for the `Validated`. If you import or mixin `ValidatedValues` you'll be able to call `.value` to extract
`Valid` and `.invalidValue` to extract the `Invalid` side.

## Documentation and Support

- See the [scaladoc](https://javadoc-badge.appspot.com/com.ironcorelabs/cats-scalatest_2.11).
- The [tests](https://github.com/IronCoreLabs/cats-scalatest/tree/main/src/test/scala/cats/scalatest) show usage.
- Yell at [@IronCoreLabs](https://twitter.com/ironcorelabs) or [@coltfred](https://twitter.com/coltfred) on twitter.
- Drop by the cats [gitter](https://gitter.im/non/cats).

## Contributors

- [Colt Frederickson](http://github.com/coltfred) [coltfred]

Idea ported from [scalaz-scalatest](https://github.com/typelevel/scalaz-scalatest), which is
primarily written by [Brendan McAdams](https://github.com/bwmcadams).
