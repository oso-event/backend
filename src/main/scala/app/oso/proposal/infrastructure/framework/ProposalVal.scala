package app.oso.proposal.infrastructure.framework

import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal

object ProposalVal {

  def unapply(id: String)(implicit service:ProposalService): Option[Proposal] = service.find(id)
}
