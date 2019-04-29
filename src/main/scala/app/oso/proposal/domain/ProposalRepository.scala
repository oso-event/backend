package app.oso.proposal.domain

trait ProposalRepository {
  def findAll:List[Proposal]
  def find(id:Int):Proposal
  def create(proposal: Proposal):Proposal
  def update(proposal: Proposal):Proposal
}
