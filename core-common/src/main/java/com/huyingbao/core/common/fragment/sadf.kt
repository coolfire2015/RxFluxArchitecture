package com.huyingbao.core.common.fragment

class sadf {
    fun onCrashHandleStart2GetExtraDatas(crashType: Int, errorType: String, errorMessage: String, errorStack: String): ByteArray? {
        return try {
            "Extra data.".toByteArray(charset("UTF-8"))
        } catch (e: Exception) {
            null
        }

    }
}
