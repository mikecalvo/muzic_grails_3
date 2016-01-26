package muzic

class Play {

  Date timestamp

  static belongsTo = [song: Song]
  static hasMany = [comments: PlayComment]
}
