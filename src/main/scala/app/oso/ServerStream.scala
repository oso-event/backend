package app.oso

import app.oso.proposal.application.ProposalService
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import app.oso.votation.application.VotationService
import app.oso.votation.infrastructure.VotationController
import cats.effect.{Effect, IO}
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object ServerStream {

  val service = new ProposalService(new InMemoryProposalRepository())

  def ProposalController[F[_]: Effect] = new ProposalController[F](service).controller

  def VotationController[F[_]: Effect] = new VotationController[F](new VotationService, new InMemoryProposalRepository).controller

  def stream[F[_]: Effect](implicit ec: ExecutionContext) =
    BlazeBuilder[F]
      .bindHttp(8088, "0.0.0.0")
      .mountService(ProposalController[F], "/")
      .mountService(VotationController[F], "/")
      .serve
}