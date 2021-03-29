package com.manuelcarvalho.speccygraphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {

    lateinit var frame: FrameLayout

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
    }
}