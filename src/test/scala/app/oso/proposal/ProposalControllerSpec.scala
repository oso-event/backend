package app.oso.proposal

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.dto.{ProposalAdminDTO, ProposalDTO}
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
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
      val body     = ProposalDTO(title = Some("Test"), speakers = Some("Test")).asJson.toString
      val endpoint = Uri.fromString(s"/${ProposalController.ENDPOINT_BASE}").right.get
      val request  = Request[IO](Method.POST, endpoint).withBody(body).unsafeRunSync()
      executeController(request).status must beEqualTo(Status.Ok)
    }
    "update proposal" >> {
      val body     = ProposalAdminDTO(title = Some("Test"), speakers = Some("Test"), visible =  Some(true)).asJson.toString
      val endpoint = Uri.fromString(s"/${ProposalController.ENDPOINT_BASE}/1").right.get
      val request  = Request[IO](Method.PATCH, endpoint).withBody(body).unsafeRunSync()
      executeController(request).status must beEqualTo(Status.Ok)
    }
    "update proposal with partial information" >> {
      val body     = ProposalAdminDTO(visible =  Some(false)).asJson.toString
      val endpoint = Uri.fromString(s"/${ProposalController.ENDPOINT_BASE}/1").right.get
      val request  = Request[IO](Method.PATCH, endpoint).withBody(body).unsafeRunSync()
      executeController(request).status must beEqualTo(Status.Ok)
    }
  }
}
