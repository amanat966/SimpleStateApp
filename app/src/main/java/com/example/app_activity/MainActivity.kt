package com.example.app_activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.app_activity.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInc.setOnClickListener{
            getIncrement()
        }
        binding.btnColor.setOnClickListener{
            setRandomColor()
        }
        binding.btnVisibility.setOnClickListener{
            switchVisibility()
        }

    }
    private fun getIncrement(){
        var counter = binding.tvCounter.text.toString().toInt()
        counter++
        binding.tvCounter.setText(counter.toString())

    }
    private fun setRandomColor(){
        val randomColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        binding.tvCounter.setTextColor(randomColor)
    }
    private fun switchVisibility() = with(binding.tvCounter){
        visibility = if(visibility == VISIBLE)
            INVISIBLE
        else
            VISIBLE
    }

}