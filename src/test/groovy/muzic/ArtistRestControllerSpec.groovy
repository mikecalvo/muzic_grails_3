package muzic

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Specification

@TestFor(ArtistRestController)
@Mock([Artist])
class ArtistRestControllerSpec extends Specification {

  def 'lists all artists'() {
    given:
    new Artist(name: 'Stereolab').save(failOnError: true, flush: true)

    when:
    controller.index()

    then:
    response.json*.name.find { it == 'Stereolab' }
  }

  def 'returns a single artist'() {
    given:
    def id = new Artist(name: 'Stereolab').save(failOnError: true, flush: true).id

    and:
    controller.params.id = id

    when:
    controller.show()

    then:
    response.json.name == 'Stereolab'
  }

  def 'creates an artist'() {
    given:
    request.method = 'POST'
    controller.request.json = '{"name": "Stereolab", "class": "muzic.Artist"}'

    when:
    controller.save()

    then:
    response.status == 201
    response.json.id != null
  }

  def 'edits an artist'() {
    given:
    request.method = 'PUT'
    def id = new Artist(name: 'Stereolab').save(failOnError: true, flush: true).id

    and:
    controller.params.id = id

    and:
    controller.request.json = '{"class": "muzic.Artist", "name": "The Groop", "id": ' + id + '}'

    when:
    controller.update()

    then:
    response.status == 200
    response.json.id != null
    response.json.name == 'The Groop'
  }

  def 'searches for an artist'() {
    given:
    def headyArtists = ['Portishead', 'Talking Heads', 'Radiohead', 'Murray Head', 'Big Head Todd']
    def headlessArtists = ['Stereolab', 'Joy Division']
    request.method = 'GET'
    (headlessArtists + headyArtists).each {
      new Artist(name: it).save()
    }

    when:
    controller.index(5, 'head')
    def json = response.json

    then:
    response.status == 200
    json.size() == headyArtists.size()
    headyArtists.each { String name ->
      assert json.find { it.name == name }
    }
  }
}
