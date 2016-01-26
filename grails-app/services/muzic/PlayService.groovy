package muzic
import grails.converters.JSON

class PlayService {

  static def TOPIC = 'songPlayed'

  static transactional = false

  def songPlayed(String title, String artistName) {

    def song = Song.findByTitleAndArtist(title, Artist.findByName(artistName))
    if (song) {
      return recordPlay(song)
    }

    def artist = Artist.findByName(artistName)
    if (artist) {
      song = new Song(title: title, artist: artist).save()
      return recordPlay(song)
    }

    song = new Song(title: title, artist: new Artist(name: artistName).save()).save()
    return recordPlay(song)
  }

  private def recordPlay(Song song) {
    def before = Play.count()
    def play = Song.withTransaction {
      new Play(song: song, timestamp: new Date()).save(flush: true, failOnError: true)
    }
    assert Play.count() == before+1

    def messageString = (play as JSON) as String
    log.info("Sending song message: ${messageString}")
    event(TOPIC, messageString, [fork: true])
    println '********* On songPlayed end: songs='+Song.count+' artists='+Artist.count+' profiles='+Profile.count
    return play
  }
}
