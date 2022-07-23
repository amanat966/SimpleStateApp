package com.example.app_activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.app_activity.constance.Constance
import com.example.app_activity.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class OnSaveInstanceStateActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var counterValue by notNull<Int>()
    private var counterColor by notNull<Int>()
    private var counterVisible by notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInc.setOnClickListener {
            increment()
        }
        binding.btnColor.setOnClickListener {
            color()
        }
        binding.btnVisibility.setOnClickListener {
            visibility()
        }

        if (savedInstanceState == null) {
            counterValue = 0
            counterColor = ContextCompat.getColor(this,R.color.black)
            counterVisible = true
        }
        else {
            counterValue = savedInstanceState.getInt(KEY_COUNTER)
            counterColor = savedInstanceState.getInt(KEY_COLOR)
            counterVisible = savedInstanceState.getBoolean(KEY_IS_VISIBLE)
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(KEY_COUNTER, counterValue)
            putInt(KEY_COLOR, counterColor)
            putBoolean(KEY_IS_VISIBLE, counterVisible)
        }
    }

    private fun increment(){
        counterValue++
        renderState()
    }
    private fun color(){
        counterValue = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun renderState()= with(binding) {
        btnInc.setText(counterValue.toString())
        btnColor.setTextColor(counterColor)
        btnVisibility.visibility = if (counterVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun visibility(){
        counterVisible = !counterVisible
        renderState()
    }
    companion object {
        @JvmStatic private val KEY_COUNTER = "COUNTER"
        @JvmStatic private val KEY_COLOR = "COLOR"
        @JvmStatic private val KEY_IS_VISIBLE = "IS_VISIBLE"
    }
}