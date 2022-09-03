package com.swastik.wallpaperadminpanel.Activitys

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Adapters.CatAdapter
import com.swastik.wallpaperadminpanel.Model.BomModel
import com.swastik.wallpaperadminpanel.databinding.ActivityFinalBinding

class FinalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val id = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")

        binding.finalTittle.text = name.toString()
        binding.finalHeading.text = name.toString() + " Wallpaper"


        db.collection("categories").document(id.toString()).collection("wallpaper")
            .addSnapshotListener { value, error ->

                if (value != null) {

                    val catlist = ArrayList<BomModel>()
                    val data = value.toObjects(BomModel::class.java)

                    catlist.addAll(data)
                    binding.finalRcv.layoutManager = GridLayoutManager(this, 2)
                    binding.finalRcv.adapter = CatAdapter(this, catlist,id)


                } else {

                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }

        binding.finalButton.setOnClickListener {
            if (binding.finalLink.toString().trim { it <= ' ' }.isNotEmpty()) {


                val muid = db.collection("categories").document().id
                val finaldata = BomModel(id = muid, link = binding.finalLink.text.toString())

                db.collection("categories").document(id!!).collection("wallpaper").document(muid).set(finaldata)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, "Wallpaper Added Succsesfully", Toast.LENGTH_SHORT)
                                .show()
                            binding.finalLink.setText("")
                        } else {

                            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                            binding.finalLink.setText("")
                        }
                    }
            } else {
                Toast.makeText(this, "Enter valid Link OR Color", Toast.LENGTH_SHORT).show()
            }
        }

    }
}