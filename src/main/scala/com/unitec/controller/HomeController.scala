
package com.unitec.controller

import org.json4s.DefaultFormats
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.{ read, write }
import org.json4s.native.Serialization
import org.scalatra.CorsSupport
import org.scalatra.ScalatraServlet
import org.slf4j.LoggerFactory
import com.unitec.service.MembroService
import com.unitec.dao._
import com.unitec.dao.MembroDao
import com.unitec.dao.OrcamentoDao
import com.unitec.dao.LocalDao
import com.unitec.service.MembroService
import com.unitec.dao.CompraDao
import com.unitec.model.Mensagens

class HomeController() extends ScalatraServlet with CorsSupport {
  implicit val formats = Serialization.formats(NoTypeHints)
  val logger = LoggerFactory.getLogger(getClass)

  before() {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
    response.setHeader("Access-Control-Max-Age", "3600")
  }


  post("/valida") {
    try {
      logger.info("Validando usuario 1!" + request.body)
      val usuario = read[Usuario](request.body)
      write(usuario.copy(isValido = MembroService.isMembroValido(usuario)))
    
    } catch {
      case e: Exception => write(Mensagens("ERROR", "Erro ao fazer o login"))
    }

  }

  get("/createTables") {
    logger.warn("Criando tablelas no banco de dados! -")
    LocalDao.createTable()
    MembroDao.createTable()
    OrcamentoDao.createTable()
    OrcamentoDao.createTableM()
    CompraDao.createTable()
    MembroDao.criaMembro()
  }

}

case class Usuario(email: String, senha: String, val isValido: Boolean = false)