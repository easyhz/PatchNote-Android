package com.easyhz.patchnote.core.common.helper.web

import android.content.Context
import android.content.Intent
import android.net.Uri

class WebHelper {
    fun openWebPage(context: Context, url: String) {
        navigateToUrl(context = context, url = url)
    }


    private fun navigateToUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}