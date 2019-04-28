package app.oso.proposal.application

import app.oso.proposal.domain.{Proposal, ProposalRepository}


class ProposalService (repository: ProposalRepository) {
  def list: List[Proposal] = repository.findAll
  def create(proposal: Proposal): Proposal = repository.create(proposal)
}