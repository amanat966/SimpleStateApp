package com.example.app_activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
            savedInstanceState.getParcelable(KEY_STATE)!!
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
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
            ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(counterValue)
            parcel.writeInt(counterColor)
            parcel.writeByte(if (counterVisible) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<State> {
            override fun createFromParcel(parcel: Parcel): State {
                return State(parcel)
            }

            override fun newArray(size: Int): Array<State?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }
}