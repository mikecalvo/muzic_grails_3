package muzic

class ProfileService {

  def updateProfile(String username, def params) {
    def user = User.findByUsername(username)
    if (!user) {
      throw new RuntimeException("No user found: $username")
    }
    def profile = Profile.findByUser(user)
    if (!profile) {
      profile = new Profile(user: user)
    }

    profile.email = params.email
    profile.save()

    return profile
  }
}
