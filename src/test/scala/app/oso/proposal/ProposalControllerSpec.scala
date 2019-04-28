package app.oso.proposal

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import cats.effect.IO
import org.http4s.{HttpService, Method, Request, Response, Status, Uri}
import org.specs2.mutable.Specification

class ProposalControllerSpec extends Specification {

  var controller:HttpService[IO] = _

  def getController: HttpService[IO] = {
    val service    = new ProposalService(new InMemoryProposalRepository())
    new ProposalController[IO](service).controller
  }

  def executeController(request: Request[IO]): Response[IO] =
    getController.orNotFound(request).unsafeRunSync()


  "Proposals" >> {
    "list proposals" >> {
      executeController(Request[IO](Method.GET, Uri.uri("/proposals"))).status must beEqualTo(Status.Ok)
    }
    "post proposal" >> {
      val body    = Proposal(title = "Test", id=2, speakers = "Test", votes = 1, visible =  true).asJson.toString
      val request = Request[IO](Method.POST, Uri.uri("/proposals")).withBody(body).unsafeRunSync()
      executeController(request).status must beEqualTo(Status.Ok)
    }
  }
}
