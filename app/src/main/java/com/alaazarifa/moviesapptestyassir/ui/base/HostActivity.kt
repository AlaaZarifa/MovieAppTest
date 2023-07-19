package com.alaazarifa.moviesapptestyassir.ui.base

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.alaazarifa.moviesapptestyassir.databinding.ActivityHostBinding


class HostActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHostBinding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    // this function gives control weather to make the status-bar transparent
    // and allow the ImageView to blend all the way to the top of the screen.
    fun setStatusBarTransparent(transparent: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, transparent.not())
        window.statusBarColor = if (transparent) Color.TRANSPARENT else Color.WHITE
    }

}
