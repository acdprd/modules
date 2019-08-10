package com.acdprd.network.exceptions

class ApiError {

    var code: Int? = null
    var name: String? = null
    var message: String? = null
    var type: String? = null
    var status: Int? = null

    fun getError(): String {
        val sb = StringBuffer()
        if (code != null) {
            sb.append("Code: ")
            sb.append((code).toString())
            sb.append("\n")
        }
        if (message != null) {
            sb.append(message)
        }
        return sb.toString()
    }
}
