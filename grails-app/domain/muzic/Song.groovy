package muzic

import grails.rest.Resource

@Resource(uri='/api/songs', formats = ['json', 'xml'])
class Song {

  String title
  String lyrics
  Long releaseYear

  static belongsTo = [artist: Artist]

  static constraints = {
    lyrics nullable: true
    releaseYear nullable: true
  }
}
