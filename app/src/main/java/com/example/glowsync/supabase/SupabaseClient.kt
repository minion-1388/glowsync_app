package com.example.glowsync.supabase

import com.example.glowsync.BuildConfig

object SupabaseClient {
    val url: String = BuildConfig.SUPABASE_URL.trimEnd('/')
    val anonKey: String = BuildConfig.SUPABASE_ANON_KEY
    val isConfigured: Boolean get() = url.isNotBlank() && anonKey.isNotBlank()
}
