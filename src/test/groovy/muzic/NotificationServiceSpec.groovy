package muzic

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import spock.lang.Specification

@TestFor(NotificationService)
@Mock([Artist, Profile, Song, Profile, ProfileMessage, Follow, User, Play])
@TestMixin([ControllerUnitTestMixin])
class NotificationServiceSpec extends Specification {

  def "saved profile message when received"() {
    given:
    def user = new User(username: 'dude', password: 'xxxxxx').save(flush: true)
    def profile = new Profile(email: 'a@b.com', dateCreated: new Date(), user: user).save(flush: true, failOnError: true)
    def artist = new Artist(name: 'Portishead').save(flush: true, failOnError: true)
    def song = new Song(title: 'Sour Times', artist: artist).save(flush: true, failOnError: true)
    new Follow(profile: profile, artist: artist).save(flush: true, failOnError: true)
    def play = new Play(song: song, timestamp: new Date()).save(flush: true, failOnError: true)
    def payload = (play as JSON) as String

    when:
    service.songPlayed(payload)

    then:
    ProfileMessage.count() == 1
    ProfileMessage.findByProfile(profile).text.contains('Sour Times')
  }
}
