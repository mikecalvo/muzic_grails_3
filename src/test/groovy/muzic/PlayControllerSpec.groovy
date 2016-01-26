package muzic

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PlayController)
@Mock([Song, Artist, Play])
class PlayControllerSpec extends Specification {

  void "returns plays with song and artist information"() {
    given:
    def creep = new Song(title: 'Creep', artist: new Artist(name: 'Radiohead').save()).save()
    new Play(song: creep, timestamp: new Date()).save()

    when:
    controller.index()

    then:
    response.text.contains('Creep')
    response.text.contains('Radiohead')
  }
}
