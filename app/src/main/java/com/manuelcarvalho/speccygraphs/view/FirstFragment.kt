package com.manuelcarvalho.speccygraphs.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelcarvalho.speccygraphs.R
import com.manuelcarvalho.speccygraphs.viewmodel.AppViewModel

private const val TAG = "FirstFragment"
class FirstFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
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

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        val bartBmp = BitmapFactory.decodeResource(resources,
                R.drawable.bart)

        val bart2 = resize(resources.getDrawable(R.drawable.bart))


        var view1 = activity?.let { ScreenCanvas(it.applicationContext) }
        frame = view.findViewById<FrameLayout>(R.id.frameFirst)!!

        //frame.addView(view1)

        imageV = view.findViewById<ImageView>(R.id.imageView)
        //imageV.setImageDrawable(resources.getDrawable(R.drawable.bart))

        //imageV.setImageBitmap(bart2)
        observeViewModel()
    }

    private fun resize(image: Drawable): Bitmap? {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, 256, 192, false)
        return bitmapResized
    }

    private fun observeViewModel() {
        Log.d(TAG, "ObserveViewModel started")
        viewModel.newImage.observe(viewLifecycleOwner, Observer { image ->
            image?.let {
                imageV.setImageBitmap(image)

            }
        })

    }
}