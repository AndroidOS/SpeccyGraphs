package com.manuelcarvalho.speccygraphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment



class FirstFragment : Fragment() {

    lateinit var frame: FrameLayout
    private lateinit var imageV: ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var view1 = activity?.let { ScreenCanvas(it.applicationContext) }
        frame = view.findViewById<FrameLayout>(R.id.frameFirst)!!

        frame.addView(view1)

        imageV = view.findViewById<ImageView>(R.id.imageView)
        imageV.setImageDrawable(resources.getDrawable(R.drawable.bart))
    }
}