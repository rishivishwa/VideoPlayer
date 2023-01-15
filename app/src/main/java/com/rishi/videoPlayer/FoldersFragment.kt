package com.rishi.videoPlayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.rishi.videoPlayer.databinding.FragmentFoldersBinding


class FoldersFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireContext().theme.applyStyle(MainActivity.themesList[MainActivity.themeIndex], true)
        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        val binding = FragmentFoldersBinding.bind(view)
        binding.FoldersRV.setHasFixedSize(true)
        binding.FoldersRV.setItemViewCacheSize(10)
        binding.FoldersRV.layoutManager = LinearLayoutManager(requireContext())
        binding.FoldersRV.adapter = FoldersAdapter(requireContext(), MainActivity.folderList)
        binding.totalFolders.text = "Total Folders: ${MainActivity.folderList.size}"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        refreshHomeScreenAd(true)
    }

    private fun refreshHomeScreenAd(showVideo: Boolean?) {
        try {
                val adView = layoutInflater
                    .inflate(R.layout.dark_main_native_ad_layout, null) as? NativeAdView?
                //  populateHomeScreenAd(adView)
                if (context != null) {
                    activity?.let {

//                        MobileAds.initialize(requireContext(), resources.getString(R.string.ad_unit_id))

                        Utils.refreshAd(
                            false, it,getString(R.string.ad_unit_id)
                        ) {
                            if (adView != null) {
                                populateHomeScreenAd(adView, it)
                            }
                        }
                    }
                }


        }catch (e:Exception){

        }
    }

    private fun populateHomeScreenAd(adView: NativeAdView?,unifiedNativeAd : NativeAd?) {
        if(unifiedNativeAd != null)
        {
//            var adDisplayDelayTime: Long? = (context as? Activity?)?.let {
//                RemotConfigUtils.getHomeAdDisplayTime(
//                    it
//                )
//            }
//            if (adDisplayDelayTime != null) {
//                if (adDisplayDelayTime < homeAdDisplayTime)
//                    adDisplayDelayTime = 0
//
//                Handler(Looper.myLooper()!!).postDelayed(
//                    {
//                        adViewContainer_update?.visibility = View.VISIBLE
//                        home_ad_holder_update?.visibility = View.GONE
//                        scrollview?.invalidate()
//                        var activityDestroyed = false
////                    activityDestroyed = isDestroyed
////                    if (activityDestroyed || isFinishing || isChangingConfigurations) {
////                        unifiedNativeAd.destroy()
////                        NativeAdSingeleton.getInstance().setNativeAd(null)
////                        return@postDelayed
////                    }
//                        // You must call destroy on old ads when you are done with them,
//                        // otherwise you will have a memory leak.
//                        //   currentUnifiedNativeAd.destroy()
//                        //   currentUnifiedNativeAd = unifiedNativeAd
                        NativeAdSingeleton.getInstance().setNativeAd(unifiedNativeAd)
                        populateUnifiedNativeAdView(unifiedNativeAd, adView)
//                        adViewContainer_update?.removeAllViews()
//                        adViewContainer_update?.addView(adView)
//                        ad_container_update?.doVisible()
//                    }, adDisplayDelayTime
//                )
//            }
        }
    }
    private fun populateUnifiedNativeAdView(
        nativeAd: NativeAd?,
        adView: NativeAdView?)
    {
        // Set the media view.
        // adView.mediaView = adView.findViewById<ImageView>(R.id.ad_media)


        if(nativeAd?.mediaContent != null)
            adView?.findViewById<ImageView>(R.id.ad_media)?.setImageDrawable(nativeAd?.mediaContent?.mainImage)
        adView?.findViewById<ImageView>(R.id.ad_media)?.setImageDrawable(nativeAd?.mediaContent?.mainImage)
        // Set other ad assets.
        adView?.headlineView = adView?.findViewById(R.id.ad_headline)
        // if(showAdBody)
        adView?.bodyView = adView?.findViewById(R.id.ad_body)
        adView?.callToActionView = adView?.findViewById(R.id.ad_call_to_action)
        adView?.iconView = adView?.findViewById(R.id.ad_app_icon)

        (adView?.headlineView as? TextView?)?.text = nativeAd?.headline

        if(nativeAd?.body != null)
            (adView?.bodyView as? TextView?)?.text = nativeAd.body

        if (nativeAd?.callToAction == null) {
            adView?.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView?.callToActionView?.visibility = View.VISIBLE
            (adView?.callToActionView as? TextView)?.text = nativeAd.callToAction
        }

        if (nativeAd?.icon == null) {
            adView?.iconView?.visibility = View.GONE
        } else {
            (adView?.iconView as? ImageView?)?.setImageDrawable(
                nativeAd.icon?.drawable
            )
            adView?.iconView?.visibility = View.VISIBLE
        }

        if (nativeAd != null) {
            adView?.setNativeAd(nativeAd)
        }
    }
}