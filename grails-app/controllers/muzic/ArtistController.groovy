package muzic

import grails.converters.JSON

class ArtistController {

  def index() {
    def count = params.count != null ? Math.min(params.count, 10) : 10
    render Artist.list(max: count) as JSON
  }

  // Maps to /artist/get/id
  def get() {
    def artist = Artist.get(params.id)
    if (!artist) {
      response.sendError(404)
    } else {
      [artist: artist]
    }
  }
}
