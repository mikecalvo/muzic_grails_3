package muzic

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification
import spock.lang.Unroll

@Integration
@Rollback
class PlayServiceSpec extends Specification {

  def playService

  def artist
  def song
  def profile
  def follow

  def setup() {
    artist = new Artist(name: 'The Cure').save(failOnError: true, flush: true)
    song = new Song(title: 'Just Like Heaven', artist: artist).save(failOnError: true, flush: true)
    profile = Profile.findByEmail('me@test.com')
    follow = new Follow(artist: artist, profile: profile).save(failOnError: true, flush: true)
  }

  def cleanup() {
    follow.delete()
    song.delete()
    artist.delete()
  }

  @Unroll('#description')
  def 'song play is saved'() {
    given:
    int startingPlayCount = Play.count()

    when:
    playService.songPlayed(title, artistName)

    then:
    Song.findByTitle(title)
    Artist.findByName(artistName)
    Play.count() == startingPlayCount + 1

    where:
    description           | title              | artistName
    'New song and artist' | 'Remote Control'   | 'The Clash'
    'New song'            | "Boys Don't Cry"   | 'The Cure'
    'Existing song'       | 'Just Like Heaven' | 'The Cure'
  }

}
