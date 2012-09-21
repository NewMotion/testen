package com.thenewmotion.testen.lift

import org.specs2.mutable.Specification
import org.specs2.matcher.{Matcher, Matchers}
import net.liftweb.http.{S, LiftResponse, Req, LiftRules}
import LiftDispatch.Must

trait LiftDispatch extends Specification with MockRequests with Matchers with LiftMatchers {

  def inStatelessSession(service: LiftRules.DispatchPF, req: Req) =
    new Must((), service, req)

  def inStatelessSession(inits: => Unit)(service: LiftRules.DispatchPF, req: Req) =
    new Must(inits, service, req)
}

object LiftDispatch extends LiftDispatch {
  class Must(inits: => Unit,
             service: LiftRules.DispatchPF,
             req: Req) {
    def responseMust(checks: => Matcher[LiftResponse]) = {
      S.statelessInit(req) {
        inits
        req must beServedBy(service)
        service(req)() must bePresent and ifPresent ^^ checks
      }
    }
  }
}
