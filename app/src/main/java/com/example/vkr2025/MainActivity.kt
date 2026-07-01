package com.example.vkr2025


import android.app.ActivityOptions
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация кнопок
        initButtons()
    }


    private fun initButtons() {
        // Кнопка перехода в режим обучения
        findViewById<MaterialButton>(R.id.btnLearning1).setOnClickListener {
            startActivityWithAnimation(LearningActivity::class.java)
        }

        // Кнопка перехода к выбору теста
        findViewById<MaterialButton>(R.id.btnLearning2).setOnClickListener {
            startActivityWithAnimation(TestSelectionActivity::class.java)
        }

        // Кнопка перехода к результатам
        findViewById<MaterialButton>(R.id.btnLearning3).setOnClickListener {
            startActivityWithAnimation(ResultsActivity::class.java)
        }
    }


    private fun startActivityWithAnimation(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}