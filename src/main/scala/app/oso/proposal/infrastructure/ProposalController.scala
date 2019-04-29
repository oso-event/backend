package app.oso.proposal.infrastructure

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.framework.ProposalVal
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.circe.CirceEntityDecoder._

class ProposalController[F[_]: Effect](service:ProposalService) extends Http4sDsl[F] {

  implicit val serviceForProposalVal:ProposalService = service

  val controller: HttpService[F] = HttpService[F]{

      case GET  -> Root / ProposalController.ENDPOINT_BASE => Ok(service.list.asJson)

      case req @ POST -> Root / ProposalController.ENDPOINT_BASE => {
        req.decode[Proposal] {
          data => {
            Ok(service.create(data).asJson)
          }
        }
      }

      case req @ PATCH -> Root / ProposalController.ENDPOINT_BASE / ProposalVal(proposal) => {
        req.decode[ProposalDTO] {
          data => {
            val updatedProposal = Proposal(
              speakers = data.speakers.getOrElse(proposal.speakers),
              title    = data.title.getOrElse(proposal.title),
              visible  = data.visible.getOrElse(proposal.visible),
              votes    = proposal.votes,
              id       = proposal.id
            )
            Ok(service.update(updatedProposal).asJson)
          }
        }
      }
  }

}
object ProposalController{
  val ENDPOINT_BASE = "proposals"
}
