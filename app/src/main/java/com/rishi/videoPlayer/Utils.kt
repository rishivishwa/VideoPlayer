package com.rishi.videoPlayer

import android.app.Activity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAd

object Utils {
    fun refreshAd(
        showVideo: Boolean,
        activity: Activity,
        adUnitId : String,
        onAdLoaded: (mNativeAd: NativeAd) -> Unit
    ) {
            val builder = AdLoader.Builder(activity, adUnitId)
            builder.forNativeAd { NativeAd ->
                var activityDestroyed = false
                activityDestroyed = activity.isDestroyed
                if (activityDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                    NativeAd.destroy()
                    return@forNativeAd
                }
                //  NativeAdSingeleton.getInstance().mNativeAd?.destroy()
                NativeAdSingeleton.getInstance().setNativeAd(NativeAd)
                onAdLoaded.invoke(NativeAd)
            }
            if (showVideo) {
                val videoOptions = VideoOptions.Builder()
                    .setStartMuted(showVideo)
                    .build()

                val adOptions = NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build()

                builder.withNativeAdOptions(adOptions)
            }

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    //   val error = """" domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}  """
                }
            }).build()

            adLoader.loadAd(AdRequest.Builder().build())

    }
}