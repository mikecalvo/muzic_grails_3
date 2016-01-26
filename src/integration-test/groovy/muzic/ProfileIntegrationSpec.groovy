package muzic

import spock.lang.Specification

class ProfileIntegrationSpec extends Specification {

  def user
  def setup() {
    user = new User(username: 'test', password: 'test')
    user.save()
  }

  def 'saving user persists new user in database'() {
    setup:
    def profile = new Profile(email: 'mike@calvo.com', user: user)

    when:
    profile.save()

    then:
    profile.errors.errorCount == 0
    profile.id
    profile.dateCreated
    Profile.get(profile.id).email == 'mike@calvo.com'
  }

  def 'updating a user changes data'() {
    setup:
    def profile = new Profile(email: 'joe@smith.com', user: user).save(failOnError: true)
    profile.save(failOnError: true)

    when:
    def foundUser = Profile.get(profile.id)
    foundUser.email = 'jack@smith.com'
    foundUser.save(failOnError: true)

    then:
    Profile.get(profile.id).email == 'jack@smith.com'
  }

  def 'deleting an existing user removes it from the database'() {
    setup:
    def profile = new Profile(email: 'pat@ska.com', user: user)
    profile.save(failOnError: true)

    when:
    profile.delete()

    then:
    !Profile.exists(profile.id)
  }

  def 'saving a user with invalid properties fails to validate'() {
    setup:
    def profile = new Profile(email: 'not', status: 'a'*200)

    when:
    def result = profile.save()

    then:
    !result
    profile.errors.errorCount == 3
    profile.errors.getFieldError('email').rejectedValue == 'not'
  }
}
