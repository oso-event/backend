package app.oso.definitionsteps


import cats.effect.IO
import cucumber.api.scala.{EN, ScalaDsl}
import org.http4s.dsl.io._
import org.http4s.{HttpService, MediaType, Method, Request, Status, Uri, headers}
import org.scalatest.Matchers


class CommonStepDefinitions extends ScalaDsl with EN with Matchers {


  When("""a {string} request for the {string} endpoint is created"""){ (_method:String, _endpoint:String) =>{
    CommonContext.endpoint = Uri.fromString(s"${_endpoint}").right.get
    CommonContext.method   = _method match {
      case "POST"   => Method.POST
      case "PATCH"  => Method.PATCH
      case "DELETE" => Method.DELETE
      case "GET"    => Method.GET
    }
  }}

  When("""the request has the json payload:"""){ _payload:String => CommonContext.payload = _payload }

  When("""the request is sent"""){ () => {
    CommonContext.request  = Request[IO](CommonContext.method, CommonContext.endpoint).withBody(CommonContext.payload).unsafeRunSync()
    CommonContext.response = CommonContext.controller.orNotFound(CommonContext.request).unsafeRunSync()
  }}

  Then("""the response will have the {string} status code"""){ statusCode:String => {
    statusCode match {
      case "200" => CommonContext.response.status should be(Status.Ok)
      case "204" => CommonContext.response.status should be(Status.NoContent)
    }
  }}
  Then("""the response will have the {string} content type"""){ contentType:String => {
    contentType match {
      case "application/json" => CommonContext.response.contentType.get should be( headers.`Content-Type`(MediaType.`application/json`, None) )
    }
  }}

}
