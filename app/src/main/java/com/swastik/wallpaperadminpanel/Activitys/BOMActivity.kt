package com.swastik.wallpaperadminpanel.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Adapters.BomAdapter
import com.swastik.wallpaperadminpanel.Model.BomModel
import com.swastik.wallpaperadminpanel.databinding.ActivityBomactivityBinding

class BOMActivity : AppCompatActivity() {

    lateinit var binding : ActivityBomactivityBinding
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBomactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = FirebaseFirestore.getInstance()
        db.collection("bestofthemonth").addSnapshotListener { value, error ->

            if (value != null) {

                val bestofthemonthlist = arrayListOf<BomModel>()
                val data = value.toObjects(BomModel::class.java)

                bestofthemonthlist.addAll(data)


                binding.BOMRcv.adapter = BomAdapter(this, bestofthemonthlist)
                binding.BOMRcv.layoutManager = GridLayoutManager(this,3)

            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }


        binding.BOMButton.setOnClickListener {
            if (binding.BOMLink.text.toString().trim{it<=' '}.isEmpty()){
                Toast.makeText(this,"Enter Valid Link",Toast.LENGTH_SHORT).show()
            }
            else{
                addlinktodatabase(binding.BOMLink.text.toString())
            }
        }
    }

    private fun addlinktodatabase(Link: String) {

        if (Link.trim{it<=' '}.isEmpty()){
            val uid = db.collection("bestofthemonth").document().id
            val finaldata = BomModel(uid,Link)

            db.collection("bestofthemonth").document(uid).set(finaldata)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){

                        Toast.makeText(this,"Wallpaper Added Succsesfully",Toast.LENGTH_SHORT).show()
                        binding.BOMLink.setText("")
                    }
                    else{

                        Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                        binding.BOMLink.setText("")
                    }
                }
        }
        else{
            Toast.makeText(this, "Enter valid Link", Toast.LENGTH_SHORT).show()
        }
    }
}