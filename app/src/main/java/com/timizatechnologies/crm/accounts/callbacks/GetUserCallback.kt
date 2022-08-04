package com.timizatechnologies.crm.accounts.callbacks

import android.net.Uri
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.timizatechnologies.crm.models.User
import org.json.JSONException
import org.json.JSONObject


class GetUserCallback(private val mGetUserResponse: IGetUserResponse) {
  interface IGetUserResponse {
    fun onCompleted(user: User)
  }

  private val mCallback: GraphRequest.Callback
  @Throws(JSONException::class)
  private fun jsonToUser(user: JSONObject): User {
    val picture = Uri.parse(user.getJSONObject("picture").getJSONObject("data").getString("url"))
    val name = user.getString("name")
    val id = user.getString("id")
    var email: String? = null
    if (user.has("email")) {
      email = user.getString("email")
    }

    // Build permissions display string
    val builder = StringBuilder()
    val perms = user.getJSONObject("permissions").getJSONArray("data")
    builder.append("Permissions:\n")
    for (i in 0 until perms.length()) {
      builder
        .append(perms.getJSONObject(i)["permission"])
        .append(": ")
        .append(perms.getJSONObject(i)["status"])
        .append("\n")
    }
    val permissions = builder.toString()
    return User(picture, name, id, email.toString(), permissions)
  }

  val callback: GraphRequest.Callback
    get() = mCallback

  init {
    mCallback = object : GraphRequest.Callback {
      override fun onCompleted(response: GraphResponse) {
        var user: User? = null
        try {
          val userObj = response.getJSONObject() ?: return
          user = jsonToUser(userObj)
        } catch (e: JSONException) {
          // Handle exception ...
        }

        // Handled by ProfileActivity
        mGetUserResponse.onCompleted(user!!)
      }
    }
  }
}
