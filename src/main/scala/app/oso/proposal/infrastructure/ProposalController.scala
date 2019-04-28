package app.oso.proposal.infrastructure

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.circe.CirceEntityDecoder._

class ProposalController[F[_]: Effect](service:ProposalService) extends Http4sDsl[F] {

  val controller: HttpService[F] = HttpService[F]{

      case GET  -> Root / "proposals" => Ok(service.list.asJson)

      case req @ POST -> Root / "proposals" => {

        req.decode[Proposal] {
          data => {
            Ok(service.create(data).asJson)
          }
        }


      }

  }



}
