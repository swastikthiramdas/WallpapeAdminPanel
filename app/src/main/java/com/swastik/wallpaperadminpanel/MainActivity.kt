package com.swastik.wallpaperadminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swastik.wallpaperadminpanel.Activitys.BOMActivity
import com.swastik.wallpaperadminpanel.Activitys.CategoriesActivity
import com.swastik.wallpaperadminpanel.Activitys.TCTActivity
import com.swastik.wallpaperadminpanel.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bestofthemonth.setOnClickListener {

            intent("bom")

        }
        binding.thecolortone.setOnClickListener {
            intent("tct")
        }
        binding.categories.setOnClickListener {
            intent("cat")
        }
    }

    private fun intent(pass: String) {
        when(pass){
            "bom"->{
                val intent = Intent(this,BOMActivity::class.java)
                startActivity(intent)
            }
            "tct"->{
                val intent = Intent(this,TCTActivity::class.java)
                startActivity(intent)
            }
            "cat"->{
                val intent = Intent(this,CategoriesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}