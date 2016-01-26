package muzic

class Profile {

  String email
  String status
  Date dateCreated

  static belongsTo = [user: User]
  static hasMany = [messages: ProfileMessage]

  static constraints = {
    email nullable: false, email: true, unique: true
    status nullable: true, size: 0..64
  }
}
