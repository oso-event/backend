package app.oso.proposal.domain

case class Proposal (
  id: String,
  title: String,
  speakers: String,
  votes: Int,
  visible: Boolean
)