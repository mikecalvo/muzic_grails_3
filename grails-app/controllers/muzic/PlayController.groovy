package muzic

import grails.converters.JSON

class PlayController {

  static allowedMethods = [index: 'GET', delete: 'DELETE', report: 'GET']

  def playService

  def report(String title, String artistName) {
    def play = playService.songPlayed(title, artistName)
    render play as JSON
  }

  def index() {
    if (!params.max || params.max > 25) {
      params.max = 25
    }

    JSON.use('deep')
    render Play.list(max: params.max, offset: params.offset, sort: 'timestamp') as JSON
  }

  def delete() {
    if (!params.id) {
      throw new IllegalArgumentException('Missing id parameter')
    }

    def play = Play.get(params.id)
    if (!play) {
      render 404, "Song Play ${params.id} not found"
    }

    play.delete(flush: true)

    render true
  }
}
