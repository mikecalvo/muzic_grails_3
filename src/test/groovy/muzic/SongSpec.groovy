package muzic

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification

@TestFor(Song)
@TestMixin(DomainClassUnitTestMixin)
class SongSpec extends Specification {

  def 'saves a song when required fields are specified'() {
    given:
    def radiohead = new Artist(name: 'Radiohead')
    def song = new Song(title: 'Creep', artist: radiohead, releaseYear: 1993)

    when:
    song.save()

    then:
    song.id
    !song.errors.allErrors
  }

  def 'fails to save when required fields are missing'() {
    given:
    def radiohead = new Artist(name: 'Radiohead')
    def song = new Song(artist: radiohead)

    when:
    song.save()

    then:
    !song.id
    song.errors.allErrors
  }
}
