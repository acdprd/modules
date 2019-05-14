package com.acdprd.social.network.model

import com.google.gson.annotations.SerializedName

/**
 * модели для инфы по юзерам вк
 */

class VkResponse(@SerializedName("response") var values: List<VkInfo>?)

class VkInfo(
        var id: Int?,
        @SerializedName("first_name")
        var firstName: String?,
        @SerializedName("last_name")
        var lastName: String?,
        var email: String?
)