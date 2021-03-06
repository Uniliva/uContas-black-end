package com.unitec.model

import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag
import com.unitec.model.Membro.Membros

object OrcamentoEntity {
  case class Orcamento(id: Long, var desc: String, var valor: Double) extends BaseEntity

  class Orcamentos(tag: Tag) extends BaseTables[Orcamento](tag, "ORCAMENTOS") {
    def desc = column[String]("DESCRICAO")
    def valor = column[Double]("VALOR")
    def * = (id, desc, valor) <> (Orcamento.tupled, Orcamento.unapply)
  }

  
  
  case class OrcamentoMembro(idOrcamento: Long, idMembro: Long)

  class OrcamentoMembros(tag: Tag) extends Table[OrcamentoMembro](tag, "ORCAMENTO_MEMBROS") {
    //Atributos da tabela
    def orcamentoFK = column[Long]("ORCAMENTO_ID")
    def membroFK = column[Long]("MEMBRO_ID")
    //primary key
    def pk = primaryKey("PK_PARTICIPANTES", (orcamentoFK, membroFK))
    def * = (orcamentoFK, membroFK) <> (OrcamentoMembro.tupled, OrcamentoMembro.unapply)
    //foreign key
    def fk1 = foreignKey("ORCAMENTO_FK", orcamentoFK, TableQuery[Orcamentos])(_.id,onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    def fk2 = foreignKey("MEMBRO_FK", membroFK, TableQuery[Membros])(_.id,onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
 
  }

}

