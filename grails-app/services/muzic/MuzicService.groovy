package muzic

class MuzicService {

  def addPlay(String title, String artistName, Date timestamp) {

    def artist = Artist.findByName(artistName)
    if (!artist) {
      artist = new Artist(name: artistName)
      artist.save()
      if (artist.hasErrors()) {
        generateError('Error saving artist', artist)
      }
    }


    def song =  Song.findByArtistAndTitle(artist, title)
    if (!song) {
      song = new Song(title: title, artist: artist)
      song.save()
      if (song.hasErrors()) {
        generateError('Error saving song', song)
      }
    }

    if (!timestamp) {
      timestamp = new Date()
    }
    new Play(song: song, timestamp: timestamp).save()
  }

  private static def generateError(message, domain) {
    def errorMessage =  domain.errors.allErrors.join(', ')
    throw new RuntimeException("${message}: ${errorMessage}")
  }
}
