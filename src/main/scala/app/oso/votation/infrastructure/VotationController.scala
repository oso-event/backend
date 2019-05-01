package app.oso.votation.infrastructure

import app.oso.proposal.infrastructure.InMemoryProposalRepository
import app.oso.votation.application.VotationService
import app.oso.votation.infrastructure.framework.VotationDTOVal
import cats.effect.Effect
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class VotationController[F[_]: Effect](
  service:VotationService,
  proposalRepository: InMemoryProposalRepository) extends Http4sDsl[F] {

  val controller: HttpService[F] = HttpService[F]{

      case req @ POST -> Root / VotationController.ENDPOINT_BASE => {
        req.decode[VotationDTO] {
          data => {
            val votation = service.vote(data.userIdentifier, proposalRepository.find(data.proposalIdentifier))
            Ok(votation.asJson)
          }
        }
      }

      case req @ DELETE -> Root / VotationController.ENDPOINT_BASE / VotationDTOVal(data)  => NoContent()

  }
}
object VotationController{
  val ENDPOINT_BASE = "votation"
}
