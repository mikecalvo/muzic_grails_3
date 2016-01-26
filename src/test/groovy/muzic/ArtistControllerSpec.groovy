package muzic

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonSlurper
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(ArtistController)
@Mock(Artist)
class ArtistControllerSpec extends Specification {

  def parser = new JsonSlurper()

  def 'index returns list of all artists'() {
    setup:
    def allArtists = [new Artist(name: 'U2'), new Artist(name: 'Radiohead')]
    Artist.saveAll(allArtists)

    when:
    controller.index()

    then:
    response.status == 200
    response.text == '[{"class":"muzic.Artist","id":1,"name":"U2"},{"class":"muzic.Artist","id":2,"name":"Radiohead"}]'
  }

  @Unroll
  def 'supports count parameter: #description'() {
    given:
    for (int i = 1; i <= count; i++) {
      new Artist(name: "Artist #${i}").save()
    }
    params.count = count

    when:
    controller.index()
    def returned = parser.parseText(response.text)

    then:
    returned.size() == expectedReturned

    where:
    description  | count | expectedReturned
    'Zero'       | 0     | 0
    'Two'        | 2     | 2
    'Ten'        | 10    | 10
    'Cap at ten' | 30    | 10
  }

  def 'gets the requested artist'(){
    setup:
    def artist = new Artist(name: 'Portishead').save(failOnError: true)
    params.id = artist.id

    when:
    def model = controller.get()

    then:
    model.artist.id == artist.id
    model.artist.name == 'Portishead'
  }

  def 'returns 404 when requested artist is not found'() {
    setup:
    params.id = -100

    when:
    def model = controller.get()

    then:
    response.status == 404
    !model
  }
}
