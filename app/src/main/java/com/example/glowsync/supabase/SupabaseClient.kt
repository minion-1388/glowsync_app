package com.example.glowsync.supabase

object SupabaseClient {
    val url: String = "https://xgkmopckaclobxyywiXd.supabase.co"
    val anonKey: String = "sb_publishable_ZgzFevkPrZVbv_1YtZUcbg_7BL_CB2g"

    val isConfigured: Boolean
        get() = url.isNotBlank() && anonKey.isNotBlank()
}
