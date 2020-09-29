package com.example

import akka.actor.ActorSystem
import akka.persistence.jdbc.db.{ EagerSlickDatabase, SlickDatabase, SlickDatabaseProvider }
import com.typesafe.config.Config
import slick.jdbc.{ JdbcBackend, MySQLProfile }

class DatabaseProvider(system: ActorSystem) extends SlickDatabaseProvider {

  override def database(config: Config): SlickDatabase = {
    EagerSlickDatabase(JdbcBackend.Database.forConfig("db"), MySQLProfile)
  }
}
