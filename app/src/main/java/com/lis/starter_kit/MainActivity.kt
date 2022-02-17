package com.lis.starter_kit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lis.starter_kit.startup.manage.StartupManager
import com.lis.starter_kit.tasks.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StartupManager.Builder()
            .addStartup(Task1())
            .addStartup(Task2())
            .addStartup(Task3())
            .addStartup(Task4())
            .addStartup(Task5())
            .build(this)
            .start()
    }
}