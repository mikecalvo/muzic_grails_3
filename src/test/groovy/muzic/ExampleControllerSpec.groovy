package muzic

import grails.converters.JSON
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(ExampleController)
class ExampleControllerSpec extends Specification {

  def 'index responds with empty values'() {
    when:
    controller.index()
    def data = JSON.parse(response.text) as Map

    then:
    response.status == 200
    data == [headers: [:], session: [:], params: [:]]
  }

  def 'index responds with header and param values'() {
    given:
    request.addHeader('h1', 'header-value')
    params.put('p1', 'v1')

    when:
    controller.index()

    def data = JSON.parse(response.text) as Map

    then:
    response.status == 200
    data == [headers: [h1: 'header-value'], session: [:], params: [p1: 'v1']]
  }

  @Unroll
  def 'addToSession does nothing with missing parameters: #description'() {
    given:
    parameterValues.each { k, v ->
      request.addParameter(k as String, v as String)
    }

    when:
    controller.addToSession()
    def data = JSON.parse(response.text) as Map

    then:
    response.status == 200
    data == [:]
    parameterValues.keySet().each {
      assert !session.getAttribute(it as String)
    }

    where:
    description             | parameterValues
    'No key'                | [value: '1']
    'No value'              | [key: 'kk']
    'Neither key nor value' | [something: 'irrelevant']
  }

  def 'addToSession adds to the http session'() {
    given:
    request.addParameter('key', 'color')
    request.addParameter('value', 'blue')

    when:
    controller.addToSession()
    def data = JSON.parse(response.text) as Map

    then:
    response.status == 200
    data == [color: 'blue']
    session.getAttribute('color') == 'blue'
  }
}
