package com.github.chinloyal.pusher_client.pusher.listeners

import com.github.chinloyal.pusher_client.core.utils.Constants
import com.github.chinloyal.pusher_client.pusher.PusherService
import com.github.chinloyal.pusher_client.pusher.PusherService.Companion.enableLogging
import com.github.chinloyal.pusher_client.pusher.PusherService.Companion.errorLog
import com.pusher.client.channel.PrivateEncryptedChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.google.gson.JsonObject
import java.lang.Exception

class FlutterPrivateEncryptedChannelEventListener: FlutterBaseChannelEventListener(), PrivateEncryptedChannelEventListener {
    companion object {
        val instance = FlutterPrivateEncryptedChannelEventListener()
    }

    override fun onDecryptionFailure(event: String, reason: String) {
        errorLog("[PRIVATE-ENCRYPTED] Reason: $reason, Event: $event")
    }

    override fun onAuthenticationFailure(message: String, e: Exception) {
        errorLog(message)
        if(enableLogging) e.printStackTrace()
    }

    override fun onSubscriptionSucceeded(channelName: String) {
        val event = JsonObject().apply {
            addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
            addProperty("channel", channelName)
            add("user_id", null)
            add("data", null)
        }
        this.onEvent(PusherEvent(event))
        PusherService.debugLog("[PRIVATE-ENCRYPTED] Subscribed: $channelName")
    }
}