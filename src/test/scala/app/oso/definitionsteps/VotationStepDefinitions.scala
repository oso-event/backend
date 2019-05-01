package app.oso.definitionsteps

import java.util

import app.oso.proposal.domain.ProposalRepository
import app.oso.proposal.infrastructure.InMemoryProposalRepository
import app.oso.votation.application.VotationService
import app.oso.votation.domain.Votation
import app.oso.votation.infrastructure.{VotationController, VotationDTO}
import cats.effect.IO
import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import io.circe.generic.auto._
import io.circe.parser.decode
import org.http4s.circe.CirceEntityDecoder._
import org.scalatest.Matchers


class VotationStepDefinitions extends ScalaDsl with EN with Matchers {

  var votations:List[Votation] = _
  val proposalRepository:ProposalRepository = new InMemoryProposalRepository()

  Before("@votation")((scenario:Scenario) => {
    CommonContext.controller = new VotationController[IO](new VotationService, proposalRepository).controller
  })

  Given("""the following votations are registered:"""){ data:util.List[VotationDTO] =>

  }

  Then("""the response will return a votation in its body with this information:"""){ body:String => {
    val desiredVotation:Votation  = decode[Votation](body).right.get
    val returnedVotation:Votation = CommonContext.response.as[Votation].unsafeRunSync()

    returnedVotation.userIdentifier should be(desiredVotation.userIdentifier)
    returnedVotation.proposal.title should be(desiredVotation.proposal.title)
    returnedVotation.proposal.speakers should be(desiredVotation.proposal.speakers)
    returnedVotation.proposal.votes should be(desiredVotation.proposal.votes)
  }}

}
