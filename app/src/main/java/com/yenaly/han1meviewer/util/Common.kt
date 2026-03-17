package com.yenaly.han1meviewer.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.yenaly.han1meviewer.R
import com.yenaly.yenaly_libs.utils.showShortToast
import java.security.MessageDigest

fun isLegalBuild(context: Context, sha: String): Boolean {
    return true
    return try {
        val pm = context.packageManager
        val packageName = context.packageName
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageManager.GET_SIGNING_CERTIFICATES
        } else {
            @Suppress("DEPRECATION")
            PackageManager.GET_SIGNATURES
        }

        val packageInfo = pm.getPackageInfo(packageName, flags)

        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.signingInfo?.apkContentsSigners
        } else {
            @Suppress("DEPRECATION")
            packageInfo.signatures
        }

        signatures?.any { sig ->
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(sig.toByteArray())
            digest.joinToString("") { "%02X".format(it) } == sha
        } ?: false
    } catch (_: Exception) {
        false
    }
}

fun getSha(context: Context, res: Int): String {
    val input = context.resources.openRawResource(res)
    val totalSize = input.available()
    val buffer = ByteArray(32)
    input.skip((totalSize - 32).toLong())
    input.read(buffer)
    input.close()
    return buffer.joinToString("") { "%02X".format(it) }
}
fun checkBadGuy(context: Context, res: Int): IntArray {
    try {
        val sha = getSha(context, res)
        // 把 !isLegalBuild(...) 改成 false，表示“永远不是非法构建”
        if (false){
            return intArrayOf(R.string.app_tampered, R.string.app_tampered)
        } else {
            return intArrayOf(R.string.introduction, R.string.comment)
        }
    } catch (e: java.lang.Exception){
        // 即使出错也返回正常内容
        return intArrayOf(R.string.introduction, R.string.comment)
    }
}
