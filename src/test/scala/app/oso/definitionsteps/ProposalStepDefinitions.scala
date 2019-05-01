package app.oso.definitionsteps

import java.util

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import cats.effect.IO
import cucumber.api.{PendingException, Scenario}
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.parser.decode
import org.http4s.circe.CirceEntityDecoder._
import cucumber.api.scala.{EN, ScalaDsl}
import org.http4s.HttpService
import org.scalatest.{BeforeAndAfter, Matchers}


class ProposalStepDefinitions extends ScalaDsl with EN with Matchers {

  Before("@proposal")((scenario:Scenario) => {
    val service    = new ProposalService(new InMemoryProposalRepository())
    CommonContext.controller = new ProposalController[IO](service).controller
  })

  Given("""the following proposals were sent to the system:"""){ data:util.List[Proposal] =>
    CommonContext.proposals = data
  }

  Then("""the response will return a proposal in its body with this information:"""){ body:String => {
    val desiredProposal:Proposal  = decode[Proposal](body).right.get
    val returnedProposal:Proposal = CommonContext.response.as[Proposal].unsafeRunSync()

    returnedProposal.title should be(desiredProposal.title)
    returnedProposal.speakers should be(desiredProposal.speakers)
    returnedProposal.visible should be (desiredProposal.visible)
    returnedProposal.votes should be (desiredProposal.votes)
  }}

}
