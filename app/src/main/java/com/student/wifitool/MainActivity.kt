package com.student.wifitool

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }

        val titleTextView = TextView(this).apply {
            text = "أداة فحص الواي فاي الأخلاقية"
            textSize = 20f
            setPadding(0, 0, 0, 30)
        }
        layout.addView(titleTextView)

        val scanButton = Button(this).apply {
            text = "بدء فحص الشبكات"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "جاري فحص الشبكات المحيطة...", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(scanButton)

        setContentView(layout)
    }
}
