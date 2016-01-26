package muzic

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(MuzicService)
@Mock([Play, Song, Artist])
class MuzicServiceSpec extends Specification {

  void "add play - generates exception with bad inputs"() {
    setup:
    int playCount = Play.count()

    when:
    service.addPlay(title, artist, null)

    then:
    thrown(RuntimeException)
    playCount == Play.count()

    where:
    title                            | artist
    null                             | 'U2'
    'Where the Streets Have No Name' | null
  }

  void "add play - creates artist and song"() {
    setup:
    int songCount = Song.count()
    int artistCount = Artist.count()
    int playCount = Play.count()
    Date when = new Date()

    when:
    service.addPlay('Desire', 'U2', when)

    then:
    songCount == Song.count() - 1
    artistCount == Artist.count() - 1
    playCount == Play.count() - 1
    Song.findByTitle('Desire')
    Artist.findByName('U2')
  }
}
