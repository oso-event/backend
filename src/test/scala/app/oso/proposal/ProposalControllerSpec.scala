package app.oso.proposal

import app.oso.proposal.application.ProposalService
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}

import org.http4s.dsl.io._
import cats.effect.IO
import org.http4s.{HttpService, Method, Request, Response, Status, Uri}
import org.specs2.mutable.Specification

class ProposalControllerSpec extends Specification {



  val ProposalController: Response[IO] = {
    val getRequest = Request[IO](Method.GET, Uri.uri("/proposals"))
    val service    = new ProposalService(new InMemoryProposalRepository())
    val controller:HttpService[IO] = new ProposalController[IO](service).controller

    controller.orNotFound(getRequest).unsafeRunSync()

  }

  "Cat" >> {
    "return 200" >> {
      ProposalController.status must beEqualTo(Status.Ok)
    }
  }
}
