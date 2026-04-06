package com.github.chinloyal.pusher_client.pusher.listeners

import com.github.chinloyal.pusher_client.core.utils.Constants
import com.github.chinloyal.pusher_client.pusher.PusherService
import com.pusher.client.channel.PresenceChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.pusher.client.channel.User
import com.google.gson.JsonObject
import java.lang.Exception

class FlutterPresenceChannelEventListener: FlutterBaseChannelEventListener(), PresenceChannelEventListener {
    companion object {
        val instance = FlutterPresenceChannelEventListener()
    }

    override fun onUsersInformationReceived(channelName: String, users: MutableSet<User>) {
        val event = JsonObject().apply {
            addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
            addProperty("channel", channelName)
            add("user_id", null)
            addProperty("data", users.toString())
        }
        this.onEvent(PusherEvent(event))
    }

    override fun userUnsubscribed(channelName: String, user: User) {
        val event = JsonObject().apply {
            addProperty("event", Constants.MEMBER_REMOVED.value)
            addProperty("channel", channelName)
            addProperty("user_id", user.id)
            add("data", null)
        }
        this.onEvent(PusherEvent(event))
    }

    override fun userSubscribed(channelName: String, user: User) {
        val event = JsonObject().apply {
            addProperty("event", Constants.MEMBER_ADDED.value)
            addProperty("channel", channelName)
            addProperty("user_id", user.id)
            add("data", null)
        }
        this.onEvent(PusherEvent(event))
    }

    override fun onAuthenticationFailure(message: String, e: Exception) {
        PusherService.errorLog(message)
        if(PusherService.enableLogging) e.printStackTrace()
    }

    override fun onSubscriptionSucceeded(channelName: String) {
        PusherService.debugLog("[PRESENCE] Subscribed: $channelName")

        val event = JsonObject().apply {
            addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
            addProperty("channel", channelName)
            add("user_id", null)
            add("data", null)
        }
        this.onEvent(PusherEvent(event))
    }
}