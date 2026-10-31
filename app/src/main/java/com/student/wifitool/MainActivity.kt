package com.student.wifitool

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // إنشاء واجهة المستخدم برمجياً بشكل مباشر وسهل
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }

        // عنوان التطبيق
        val titleTextView = TextView(this).apply {
            text = "أداة فحص الواي فاي الأخلاقية"
            textSize = 20f
            setPadding(0, 0, 0, 30)
        }
        layout.addView(titleTextView)

        // زر الفحص
        val scanButton = Button(this).apply {
            text = "بدء فحص الشبكات"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "جاري فحص الشبكات المحيطة...", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(scanButton)

        // عرض الواجهة على الشاشة
        setContentView(layout)
    }
}