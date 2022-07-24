package com.example.app_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.app_activity.databinding.ActivityMainBinding

class ViewModelActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<Class4ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInc.setOnClickListener { viewModel.increment() }
        binding.btnColor.setOnClickListener { viewModel.setRandomColor() }
        binding.btnVisibility.setOnClickListener { viewModel.switchVisible() }


            viewModel.initState(savedInstanceState?.getParcelable(KEY_VALUE) ?: Class4ViewModel.State(
                    counterValue = 0,
                    counterColor = ContextCompat.getColor(this, R.color.black),
                    counterVisible = true
            ))

        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_VALUE, viewModel.state.value)
    }

    private fun renderState(state: Class4ViewModel.State) = with(binding){
        tvCounter.setText(state.counterValue.toString())
        tvCounter.setTextColor(state.counterColor)
        tvCounter.visibility = if (state.counterVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object{
        @JvmStatic val KEY_VALUE = "STATE"
    }

}