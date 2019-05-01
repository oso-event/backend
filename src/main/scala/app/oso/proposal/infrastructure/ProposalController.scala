package app.oso.proposal.infrastructure

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.dto.{ProposalAdminDTO, ProposalDTO}
import app.oso.proposal.infrastructure.framework.ProposalVal
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.circe.CirceEntityDecoder._
import java.util.UUID.randomUUID

class ProposalController[F[_]: Effect](service:ProposalService) extends Http4sDsl[F] {

  implicit val serviceForProposalVal:ProposalService = service

  val controller: HttpService[F] = HttpService[F]{

      case GET  -> Root / ProposalController.ENDPOINT_BASE => Ok(service.list.asJson)

      case req @ POST -> Root / ProposalController.ENDPOINT_BASE => {
        req.decode[ProposalDTO] {
          data => {

            if(data.speakers.isEmpty || data.title.isEmpty)
              BadRequest()
            else {

              val proposal = Proposal(
                speakers = data.speakers.get,
                title    = data.title.get,
                visible  = false,
                votes    = 0,
                id       = randomUUID.toString
              )

              Ok(service.create(proposal).asJson)
            }


          }
        }
      }

      case req @ PATCH -> Root / ProposalController.ENDPOINT_BASE / ProposalVal(proposal) => {
        req.decode[ProposalAdminDTO] {
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
