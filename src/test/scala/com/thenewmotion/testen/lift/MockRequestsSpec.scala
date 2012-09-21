package com.thenewmotion.testen.lift

import org.specs2.mutable.Specification

class MockRequestsSpec extends Specification with MockRequests {
  "Using MockRequests one" should {
    "be able to" >> {
      "make GET requests" in {
        GET("somehost/somepage").get_?  must beTrue
        GET("somehost/somepage").post_? must beFalse
        GET("somehost/somepage").put_?  must beFalse
      }

      "make POST requests" in {
        POST("somehost/somepage").json("{}").get_?  must beFalse
        POST("somehost/somepage").json("{}").post_? must beTrue
        POST("somehost/somepage").json("{}").put_?  must beFalse
      }

      "make PUT requests" in {
        PUT("somehost/somepage").json("{}").get_?  must beFalse
        PUT("somehost/somepage").json("{}").post_? must beFalse
        PUT("somehost/somepage").json("{}").put_?  must beTrue
      }

      "make DELETE requests" in {
        DELETE("somehost/somepage").get_?  must beFalse
        DELETE("somehost/somepage").put_?  must beFalse
        DELETE("somehost/somepage").post_? must beFalse
      }
    }
  }
}
