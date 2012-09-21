package com.thenewmotion.testen
package lift

import org.specs2.mutable.Specification
import net.liftweb.http._
import net.liftweb.json._, JsonDSL._
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.OkResponse

class LiftMatchersSpec extends Specification with LiftDispatch {
  "Response matchers" should {
    "check response code" >> {
      OkResponse() must haveCode(200)
      //OkResponse()) must haveCode(400)
    }

    "check that request is served by dispatch function" in {
      val request = GET("root/leaf")
      request must beServedBy(sut)
    }

    "check that response is present and has right body" in {
      val request = GET("root/leaf")
      val response = sut(request)()

      response must bePresent and ifPresent ^^ (
        haveCode(200) and
        haveExactJson(
          """|{
             |  "answer":"ok"
             |}""".stripMargin
        )
      )
    }
  }
  "LiftDispatch" should {
    "allow to do request in session" in {
      inStatelessSession(sut, GET("root/leaf")) responseMust {
        haveCode(200) and
        haveExactJson(
          """|{
            |  "answer":"ok"
            |}""".stripMargin
        )
      }
    }
  }
}

object sut extends RestHelper {
  serve {
    case List("root","leaf") JsonGet _ => JsonResponse(("answer", "ok"))
  }
}
