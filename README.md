cats-scalatest
================

Scalatest bindings for scalaz.  Inspired by scalaz-scalatest.

Apache 2.0 licensed.


Matchers & Helpers are presently offered for testing of the following cats concepts:
* `Xor`
* `Validated`

## Setup  

We currently crossbuild for Scala 2.10 & 2.11.

## Usage

There are two ways to use the provided matchers. 

* Mix-In

```scala
class MySillyWalkSpec extends FlatSpec with Matchers with XorMatchers { 
  // ...
} 
```
This makes the matchers in `XorMatchers` available inside the scope of your test. 

You can also import explicitly from a provided object, instead.

```scala
import org.typelevel.scalatest.XorMatchers

class MySillyWalkSpec extends FlatSpec with Matchers { 
  import XorMatchers._
  // ...
}

```

Also brings the matchers into scope.

And now, the matchers themselves.

### TODO Add examples


## Contributors

* [Colt Frederickson](http://github.com/coltfred) [coltfred]

Idea ported from [scalaz-scalatest](https://github.com/typelevel/scalaz-scalatest), which is
primarily written by [Brendan McAdams](https://github.com/bwmcadams).
