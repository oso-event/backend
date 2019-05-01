package app.oso.definitionsteps

import java.util

import app.oso.proposal.domain.Proposal
import cats.effect.IO
import org.http4s.{HttpService, Method, Request, Response, Uri}

object CommonContext {

  var controller:HttpService[IO] = _
  var request:Request[IO] = _
  var response:Response[IO] = _
  var method:Method  = _
  var endpoint:Uri   = _
  var payload:String = _
  var proposals:util.List[Proposal] = _

}
