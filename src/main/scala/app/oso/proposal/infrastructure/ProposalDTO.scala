package app.oso.proposal.infrastructure

case class ProposalDTO (
  speakers:   Option[String],
  title:      Option[String],
  visible:    Option[Boolean]
)
