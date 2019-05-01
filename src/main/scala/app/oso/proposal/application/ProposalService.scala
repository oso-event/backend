package app.oso.proposal.application

import app.oso.proposal.domain.{Proposal, ProposalRepository}


class ProposalService (repository: ProposalRepository) {
  def list: List[Proposal] = repository.findAll
  def find(id:String): Option[Proposal] = Some(repository.findAll.head)
  def create(proposal: Proposal): Proposal = repository.create(proposal)
  def update(proposal: Proposal): Proposal = repository.update(proposal)
}