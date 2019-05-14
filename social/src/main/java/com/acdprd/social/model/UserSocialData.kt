package com.acdprd.social.model

class UserSocialData {
    var socialId: String? = null
    var email: String? = null
    var name: String? = null
    var surname: String? = null

    companion object {
        fun builder(): Builder {
            return UserSocialData().Builder()
        }
    }

    inner class Builder {
        fun email(email: String?): Builder {
            this@UserSocialData.email = email
            return this
        }

        fun name(name: String?): Builder {
            this@UserSocialData.name = name
            return this
        }

        fun surname(surname: String?): Builder {
            this@UserSocialData.surname = surname
            return this
        }

        fun socialId(id: String?): Builder {
            this@UserSocialData.socialId = id
            return this
        }

        fun build(): UserSocialData {
            return this@UserSocialData
        }
    }
}