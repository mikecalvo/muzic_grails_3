package muzic

class ProfileMessage {

  String text
  Date timestamp
  static belongsTo = [profile: Profile]

}
