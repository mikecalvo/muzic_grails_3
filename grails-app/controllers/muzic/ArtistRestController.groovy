package muzic

import grails.rest.RestfulController

class ArtistRestController extends RestfulController<Artist> {

  @SuppressWarnings("GroovyUnusedDeclaration")
  static responseFormats = ['json', 'xml']

  ArtistRestController() {
    super(Artist)
  }

  def index(Integer max, String q) {
    if (!q) {
      return super.index(max)
    }
    params.max = Math.min(max ?: 10, 100)

    def artists = Artist.where { name =~ "%${q.toLowerCase()}%" }.list(max: max)
    respond artists, model: [artistCount: artists.size()]
  }
}
