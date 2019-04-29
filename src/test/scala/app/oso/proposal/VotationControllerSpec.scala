package app.oso.proposal

import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import app.oso.votation.application.VotationService
import app.oso.votation.infrastructure.{VotationController, VotationDTO}
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.dsl.io._
import org.http4s.{HttpService, Method, Request, Response, Status, Uri}
import org.specs2.mutable.Specification

class VotationControllerSpec extends Specification {

  var controller:HttpService[IO] = _

  def getController: HttpService[IO] = {
    val service    = new VotationService
    val proposalRepository = new InMemoryProposalRepository
    new VotationController[IO](service, proposalRepository).controller
  }

  def executeController(request: Request[IO]): Response[IO] =
    getController.orNotFound(request).unsafeRunSync()


  "Votation" >> {

    "Add vote" >> {

      val body         = VotationDTO(userIdentifier = "test", proposalIdentifier = "1").asJson.toString
      val endpoint     = Uri.fromString(s"/${VotationController.ENDPOINT_BASE}").right.get
      val request      = Request[IO](Method.POST, endpoint)
                          .withBody(body)
                          .unsafeRunSync()
      executeController(request).status must beEqualTo(Status.Ok)
    }

    "Remove vote for user 'test' and proposal with id 1" >> {
      val endpoint     = Uri.fromString(s"/${VotationController.ENDPOINT_BASE}/test-1").right.get
      val request      = Request[IO](Method.DELETE, endpoint)
      executeController(request).status must beEqualTo(Status.NoContent)
    }

    "Remove vote with bad votation identifier" >> {
      val endpoint     = Uri.fromString(s"/${VotationController.ENDPOINT_BASE}/test").right.get
      val request      = Request[IO](Method.DELETE, endpoint)
      executeController(request).status must beEqualTo(Status.NotFound)
    }
  }
}
