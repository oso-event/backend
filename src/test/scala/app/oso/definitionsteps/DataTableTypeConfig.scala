package app.oso.definitionsteps

import java.util
import java.util.Locale.ENGLISH

import cucumber.api.{TypeRegistry, TypeRegistryConfigurer}
import java.util.Locale

import app.oso.proposal.domain.Proposal
import app.oso.votation.domain.Votation
import app.oso.votation.infrastructure.VotationDTO
import io.cucumber.datatable.{DataTableType, TableCellTransformer, TableEntryTransformer, TableRowTransformer}


class DataTableTypeConfig extends TypeRegistryConfigurer {

  override def locale(): Locale = ENGLISH

  override def configureTypeRegistry(typeRegistry: TypeRegistry): Unit = {

    typeRegistry.defineDataTableType(
      new DataTableType(classOf[Proposal], new TableEntryTransformer[Proposal] {
        override def transform(entry: util.Map[String, String]): Proposal = Proposal(
          id = entry.get("identifier"),
          title = entry.get("title"),
          visible = entry.get("visible").toBoolean,
          votes = entry.get("votes").toInt,
          speakers = entry.get("speakers")
        )
      }
      ))

    typeRegistry.defineDataTableType(
      new DataTableType(classOf[VotationDTO], new TableEntryTransformer[VotationDTO] {
        override def transform(entry: util.Map[String, String]): VotationDTO = VotationDTO(
          userIdentifier     = entry.get("userIdentifier"),
          proposalIdentifier = entry.get("proposalIdentifier")
        )
      }
      ))
  }
}
