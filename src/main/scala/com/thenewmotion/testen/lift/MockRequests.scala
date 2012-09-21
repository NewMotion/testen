package com.thenewmotion.testen.lift

import net.liftweb.http._
import net.liftweb.json._
import net.liftweb.common.Full
import org.specs2.mock.Mockito

trait MockRequests extends Mockito {

  def GET(path: String) = req(GetRequest, at(path))

  def DELETE(path: String) = req(DeleteRequest, at(path))

  case class PUT(path: String) {
    def json(body: String) = {
      val pj = req(PutRequest, at(path))
      pj.json returns Full(parse(body))
      pj
    }
  }

  case class POST(path: String) {
    def json(body: String) = {
      val pj = req(PostRequest, at(path))
      pj.json returns Full(parse(body))
      pj
    }
  }

  def req(t: RequestType, path: List[String]) = {
    val r = mock[Req]
    r.requestType    returns t
    r.weightedAccept returns Nil
    r.post_?         returns r.requestType.post_?
    r.get_?          returns r.requestType.get_?
    r.put_?          returns r.requestType.put_?
    r.path           returns ParsePath(path, "", absolute = false, endSlash = false)
    r
  }

  private def at(u: String) = u.split('/').toList
}
