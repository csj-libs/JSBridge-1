package com.xiansenliu.jstraw

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Message
import android.view.KeyEvent
import android.webkit.*

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:24 AM
 */
class JStrawWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    @Deprecated("", ReplaceWith("shouldInterceptRequest(WebView, WebResourceRequest)", "android.webkit.WebViewClient"))
    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse {
        return super.shouldInterceptRequest(view, url)
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse {
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    @Deprecated("", ReplaceWith("onReceivedError(WebView, WebResourceRequest, WebResourceError)", "android.webkit.WebViewClient"))
    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        return super.onRenderProcessGone(view, detail)
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        super.onReceivedLoginRequest(view, realm, account, args)
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
    }

    @Deprecated("", ReplaceWith("shouldOverrideUrlLoading(WebView, WebResourceRequest)", "android.webkit.WebViewClient"))
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        super.onUnhandledKeyEvent(view, event)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        super.onReceivedClientCertRequest(view, request)
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
    }

    @Deprecated("no longer called")
    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        super.onFormResubmission(view, dontResend, resend)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }
}