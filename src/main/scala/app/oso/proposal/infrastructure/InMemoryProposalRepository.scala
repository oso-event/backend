package app.oso.proposal.infrastructure

import app.oso.proposal.domain.{Proposal, ProposalRepository}
import java.util.UUID.randomUUID

class InMemoryProposalRepository extends ProposalRepository{
  override def findAll: List[Proposal] = List(
    Proposal(
      id       = randomUUID.toString,
      title    = "A title",
      speakers = "Ana Smith and Tobias Walter",
      visible  = false,
      votes    = 0)
  )

  override def find(id: String): Proposal = findAll.head

  override def create(proposal: Proposal): Proposal = proposal

  override def update(proposal: Proposal): Proposal = proposal
}
