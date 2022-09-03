package com.swastik.wallpaperadminpanel.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Adapters.CatViewAdapter
import com.swastik.wallpaperadminpanel.R
import com.swastik.wallpaperadminpanel.databinding.ActivityCategoriesBinding
import com.swastik.wallpaperapp.Model.CatModel
import com.swastik.wallpaperapp.Model.TCTModel

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoriesBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        db.collection("categories").addSnapshotListener { value, error ->

            if (value != null) {

                val catlist = ArrayList<CatModel>()
                val data = value.toObjects(CatModel::class.java)


                catlist.addAll(data)

                binding.CATRcv.layoutManager = GridLayoutManager(this, 2)

                binding.CATRcv.adapter = CatViewAdapter(this, catlist)


            } else {

                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }




        binding.CATButton.setOnClickListener {

            if (binding.CATLink.toString().trim { it <= ' ' }.isNotEmpty()
                &&
                binding.CATName.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {


                val uid = db.collection("categories").document().id
                val finaldata = CatModel(
                    id = uid,
                    name = binding.CATName.text.toString(),
                    link = binding.CATLink.text.toString()
                )

                db.collection("categories").document(uid).set(finaldata)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, "Wallpaper Added Succsesfully", Toast.LENGTH_SHORT)
                                .show()
                            binding.CATName.setText("")
                            binding.CATLink.setText("")
                        } else {

                            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                            binding.CATLink.setText("")
                            binding.CATName.setText("")
                        }
                    }
            } else {
                Toast.makeText(this, "Enter valid Link OR Color", Toast.LENGTH_SHORT).show()
            }
        }

    }
}