package com.thenewmotion.testen.lift

import org.specs2.matcher._
import net.liftweb.common._
import net.liftweb.http._
import com.thenewmotion.testen.lift.LiftMatchers._
import net.liftweb.json.parse

trait LiftMatchers extends Matchers with Expectations with MustExpectations {

  def haveCode(code: => Int) = new HaveCode(code)

  def haveExactJson(json: => String) = new HaveExactJson(json)

  def beServedBy(dpf: => LiftRules.DispatchPF) = new BeServedBy(dpf)

  def bePresent = new Matcher[Box[LiftResponse]] {
    def apply[S <: Box[LiftResponse]](value: Expectable[S]) = value.value match {
      case Full(r)             => result(test = true, "Present response meets all expectations", "", value)
      case Empty               => result(test = false, "", "No response available", value)
      case Failure(msg, e, ch) => result(test = false, "", "Failure occured during request serve %s".format(msg), value)
    }
  }

  val ifPresent = { (b: Box[LiftResponse]) => b.openOrThrowException("test") }

}

object LiftMatchers {

  val NoResponseToMatchMatcher = new Matcher[LiftResponse] {
    def apply[S <: LiftResponse](value: Expectable[S]) =
      result(test = false, "", "No Response to match", value)
  }

  class BeServedBy(dpf: LiftRules.DispatchPF) extends Matcher[Req] {
    def apply[S <: Req](value: Expectable[S]) =
      result(dpf.isDefinedAt(value.value),
        "Request is served by right dispatch function",
        "Request is not served by defined dispatch function",
        value)
  }

  class HaveCode(code: Int) extends Matcher[LiftResponse] {
    def apply[S <: LiftResponse](value: Expectable[S]) = {
      val expected = code
      val actual = value.value.toResponse.code
      result(actual == expected,
        "Response has expected code " + expected,
        "Response expected code is %d but actual %d".format(expected, actual),
        value, expected.toString, actual.toString)
    }
  }

  class HaveExactJson(expected: String) extends Matcher[LiftResponse] {
    def apply[S <: LiftResponse](value: Expectable[S]) = value.value match {
      case res: JsonResponse =>
        val actual = res.json.toJsCmd
        result(parse(actual) == parse(expected),
          "Response has expected json",
          "Response has not expected json",
          value, expected, actual)

      case _ => result(test = false,
        value.description + " is json response ",
        value.description + " is not json response",
        value)
    }
  }
}
