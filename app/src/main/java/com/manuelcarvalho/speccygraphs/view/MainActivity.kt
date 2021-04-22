package com.manuelcarvalho.speccygraphs.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manuelcarvalho.speccygraphs.R
import com.manuelcarvalho.speccygraphs.utils.formatString
import com.manuelcarvalho.speccygraphs.viewmodel.AppViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: AppViewModel
    private val filepath = "MyFileStorage"
    internal var myExternalFile: File? = null
    private lateinit var fragImage: ImageView
    private val fileName = "image.asm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var view = ScreenCanvas(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]

        //fragImage = findViewById<ImageView>(R.id.imageView)

        val bartBmp = BitmapFactory.decodeResource(application.resources,
                R.drawable.bart)

        val bart2 = resize(resources.getDrawable(R.drawable.bart))
        if (bart2 != null) {
            viewModel.decodeBitmapZX(bart2)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // fragImage.setImageBitmap(bartBmp)
            //val bart2 = resize(resources.getDrawable(R.drawable.bart))
            viewModel.newImage.value = bart2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createFile() {
        myExternalFile = File(getExternalFilesDir(filepath), fileName)
        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(formatString.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
    }

    private fun decodeImage() {
        val conf = Bitmap.Config.ARGB_8888
        val bmp =
                Bitmap.createBitmap(256, 192, conf)
    }

    private fun resize(image: Drawable): Bitmap? {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, 256, 192, false)
        return bitmapResized
    }
}