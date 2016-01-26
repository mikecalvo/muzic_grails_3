package muzic

import org.bson.types.ObjectId

class Audit {

  static mapWith = "mongo"

  ObjectId id
  String user
  String action
  Date dateCreated

  static mapping = {
    collection 'audits'
    database 'muzic'
    version: false
  }
}
