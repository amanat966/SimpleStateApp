package com.example.app_activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.app_activity.databinding.ActivityMainBinding
import java.io.Serializable
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class SeriaizableActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var state: State

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

        state = if (savedInstanceState == null) {
            State(
                counterValue = 0,
                counterColor = ContextCompat.getColor(this,R.color.black),
                counterVisible = true
            )
        }
        else {
            savedInstanceState.getSerializable(KEY_STATE) as State
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STATE, state)
    }

    private fun increment(){
        state.counterValue++
        renderState()
    }
    private fun color(){
        state.counterColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun renderState()= with(binding) {
        tvCounter.setText(state.counterValue.toString())
        tvCounter.setTextColor(state.counterColor)
        tvCounter.visibility = if (state.counterVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun visibility(){
        state.counterVisible = !state.counterVisible
        renderState()
    }
    class State (
        var counterValue: Int,
        var counterColor: Int,
        var counterVisible: Boolean
            ): Serializable
    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }
}