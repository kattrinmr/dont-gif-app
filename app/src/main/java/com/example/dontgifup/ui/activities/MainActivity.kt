package com.example.dontgifup.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.dontgifup.R
import com.example.dontgifup.databinding.ActivityMainBinding
import com.example.dontgifup.ui.view_models.MainViewModel
import com.example.dontgifup.ui.view_models.PostState
import java.net.SocketTimeoutException


class MainActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var binding: ActivityMainBinding
    private var webView: WebView? = null
    private var imageView: ImageView? = null

//    private val imageView = findViewById<ImageView>(R.id.imageView)

    private val stateObserver = Observer<PostState> {
        when(it) {
            PostState.LOADED -> onPostLoaded(binding.model?.liveDataPost?.value?.gifURL.toString())
            PostState.LOADING -> onPostLoading()
            PostState.ERROR -> onPostError(binding.model?.liveDataErrorMessage?.value.toString())
            null -> return@Observer
        }
    }

    private fun onPostLoading() {
        binding.model?.liveDataForwardBtnState?.value = false
        binding.frameLayout.removeAllViews()
    }

    private fun onPostError(message: String) {
        binding.model?.liveDataForwardBtnState?.value = true
        binding.frameLayout.removeAllViews()
    }

    private fun onPostLoaded(url: String) {
        binding.model?.liveDataForwardBtnState?.value = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                initWebView(url)
            } catch (e: SocketTimeoutException) {
                onPostError("Something went wrong...")
            }
        } else {
//            imageView.visibility = View.VISIBLE
//            Glide.with(this).load(url).into(imageView)
        }
    }

    private fun initWebView(url: String) {
        if (webView == null) {
            webView = WebView(this)
            webView!!.webViewClient = object : WebViewClient() {
                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.model?.liveDataForwardBtnState?.value = true
                }

            }
            webView?.settings?.loadWithOverviewMode = true
            webView?.settings?.useWideViewPort = true
            val linearlayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            webView?.layoutParams = linearlayoutParams
        }
        webView?.loadUrl(url)
        try {
            this.binding.frameLayout.addView(webView!!)
        } catch (e: IllegalStateException) {
            return
        }
    }

    private fun initImageView(url: String) {
        if (imageView == null) {
            imageView = ImageView(this)
            imageView!!.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            imageView!!.foregroundGravity = Gravity.CENTER and Gravity.CENTER_VERTICAL
        }
        Glide.with(this).load(url).into(imageView!!)
        this.binding.frameLayout.addView(imageView!!)
        binding.model?.liveDataBackBtnState?.value = true
    }

//    fun initDescription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mainViewModel: MainViewModel by viewModels()


        binding.model = mainViewModel
        binding.lifecycleOwner = this
        mainViewModel.liveDataState.observe(this, stateObserver)
        mainViewModel.liveDataPost.observe(this) {
            mainViewModel.liveDataBackBtnState.value = it.primaryId > 0
        }


    }

}