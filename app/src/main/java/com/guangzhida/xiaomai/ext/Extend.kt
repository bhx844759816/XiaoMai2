package com.guangzhida.xiaomai.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.guangzhida.xiaomai.utils.Base64Utils
import com.guangzhida.xiaomai.utils.RSAUtil
import kotlinx.coroutines.CoroutineScope
import java.util.regex.Pattern

/**
 * 针对字符串加密
 */
fun String.rsAEncode(): String {
    val keyPair = RSAUtil.generateRSAKeyPair(1024)
    // 获取公钥和私钥
    val aPublic = keyPair.public
    val aPublicEncoded = aPublic.encoded
    try { // 公钥加密
        val bytes =
            RSAUtil.encryptByPublicKey("java", this.toByteArray(), aPublicEncoded)
        return Base64Utils.encode(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 针对字符串解密
 */
fun String.rsADecode(): String {
    val keyPair = RSAUtil.generateRSAKeyPair(1024)
    val aPrivate = keyPair.private
    val aPrivateEncoded = aPrivate.encoded
    try { // 公钥加密
        val encodeBytes = Base64Utils.decode(this)
        // 私钥解密
        val bytes1 =
            RSAUtil.decryptByPrivateKey("java", encodeBytes, aPrivateEncoded)
        return String(bytes1)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 隐藏软键盘
 */
fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

}


internal fun EditText.showSoftInput() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    val inputManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, 0)
}
//public fun MainScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Main)

/**
 *手机号号段校验，
 *第1位：1；
 *第2位：{3、4、5、6、7、8}任意数字；
 *第3—11位：0—9任意数字
 */
internal fun String.isPhone(): Boolean {
    if (this.length == 11) {
        val compiler = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$")
        val matcher = compiler.matcher(this)
        return matcher.matches()
    }
    return false
}