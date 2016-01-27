package muzic

class ExampleController {

  // return statistics about the request and http session
  def index() {

    def data = [:]

    // collect the http session information
    data.session = [:]
    for (a in session.attributeNames) {
      data.session[a] = session.getAttribute(a)
    }

    // collect the http request headers
    data.headers = [:]
    for (h in request.headerNames) {
      data.headers[h] = request.getHeader(h)
    }

    data.params = params

    render(contentType: "application/json") {
      data
    }
  }

  // add an attribute to the http session
  // params.key is the attribute name
  // params.value is the attribute value
  def addToSession() {
    def data = (!params.key || !params.value) ? [:] : [(params.key): params.value]

    if (params.key && params.value) {
      session.setAttribute(params.key as String, params.value)
    }

    render(contentType: 'application/json') {
      data
    }
  }
}
