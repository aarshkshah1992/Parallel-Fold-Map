package base

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{FunSuite, Matchers}


trait MatchingSuite extends Matchers with TypeCheckedTripleEquals

abstract class BaseSuite extends FunSuite with MatchingSuite
