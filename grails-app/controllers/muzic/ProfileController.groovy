package muzic

class ProfileController {

  def springSecurityService
  def profileService

  def get() {
    [profile: Profile.findByUser(springSecurityService.currentUser as User)]
  }

  def update() {
    def profile = profileService.updateProfile(params.id, params)
    if (profile.hasErrors()) {
      flash.error = 'Error saving'
      flash.errors = profile.errors.allErrors
    } else {
      flash.info = 'Profile saved'
    }
    redirect(action: 'get', id: profile.user.username)
  }
}
