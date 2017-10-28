package com.xiansenliu.jstraw.wv

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.view.KeyEvent
import android.webkit.*
import com.xiansenliu.jstraw.injectJS

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:24 AM
 */
class JStrawWebViewClient(private val puppetWVC: WebViewClient?, private var jsUrl: String) : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        injectJS(view, jsUrl)
        puppetWVC?.onPageFinished(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {


        if (puppetWVC != null) {
            return puppetWVC.shouldOverrideUrlLoading(view, request)
        } else {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }


    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return puppetWVC?.shouldOverrideUrlLoading(view, url) ?: super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        return puppetWVC?.shouldInterceptRequest(view, url)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return puppetWVC?.shouldInterceptRequest(view, request)
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        return puppetWVC?.shouldOverrideKeyEvent(view, event) ?: super.shouldOverrideKeyEvent(view, event)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        puppetWVC?.doUpdateVisitedHistory(view, url, isReload)
    }

    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        puppetWVC?.onReceivedError(view, errorCode, description, failingUrl)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        puppetWVC?.onReceivedError(view, request, error)
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        return puppetWVC?.onRenderProcessGone(view, detail) ?: super.onRenderProcessGone(view, detail)
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        puppetWVC?.onReceivedLoginRequest(view, realm, account, args)
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        puppetWVC?.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        puppetWVC?.onPageStarted(view, url, favicon)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        puppetWVC?.onScaleChanged(view, oldScale, newScale)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        puppetWVC?.onPageCommitVisible(view, url)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        puppetWVC?.onUnhandledKeyEvent(view, event)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        puppetWVC?.onReceivedClientCertRequest(view, request)
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        puppetWVC?.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        puppetWVC?.onReceivedSslError(view, handler, error)
    }

    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
        puppetWVC?.onTooManyRedirects(view, cancelMsg, continueMsg)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        puppetWVC?.onFormResubmission(view, dontResend, resend)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        puppetWVC?.onLoadResource(view, url)
    }
}