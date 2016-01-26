package muzic

class PlayComment {

  String text
  Profile user

  static belongsTo = [play: Play]

}
