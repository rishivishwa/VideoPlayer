package com.rishi.videoPlayer

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdSingeleton private constructor(){

    var mNativeAd: NativeAd? = null

    companion object
    {
        private var singeleton : NativeAdSingeleton? = null

        fun getInstance() : NativeAdSingeleton
        {
            if(singeleton == null)
                singeleton = NativeAdSingeleton()
                return singeleton!!
        }
    }

    fun setNativeAd(NativeAd: NativeAd?) {
        mNativeAd = NativeAd
    }
}