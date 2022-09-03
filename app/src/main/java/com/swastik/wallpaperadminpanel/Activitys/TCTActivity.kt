package com.swastik.wallpaperadminpanel.Activitys

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Adapters.TCTAdapter
import com.swastik.wallpaperadminpanel.databinding.ActivityTctactivityBinding
import com.swastik.wallpaperapp.Model.TCTModel

class TCTActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTctactivityBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTctactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.TCTButton.setOnClickListener {


            if (binding.TCTLink.toString().trim { it <= ' ' }.isNotEmpty()
                &&
                binding.TCTColor.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {


                val uid = db.collection("thecoloreton").document().id
                val finaldata = TCTModel(
                    id = uid,
                    link = binding.TCTLink.text.toString(),
                    colore = binding.TCTColor.text.toString()
                )

                db.collection("thecoloreton").document(uid).set(finaldata)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, "Wallpaper Added Succsesfully", Toast.LENGTH_SHORT)
                                .show()
                            binding.TCTLink.setText("")
                            binding.TCTColor.setText("")
                        } else {

                            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                            binding.TCTLink.setText("")
                            binding.TCTColor.setText("")
                        }
                    }
            } else {
                Toast.makeText(this, "Enter valid Link OR Color", Toast.LENGTH_SHORT).show()
            }
        }




        db.collection("thecoloreton").addSnapshotListener { value, error ->

            if (value != null) {

                val tctlist = ArrayList<TCTModel>()
                val data = value.toObjects(TCTModel::class.java)


                tctlist.addAll(data)

                binding.TCTRcv.layoutManager = GridLayoutManager(this, 3)
                binding.TCTRcv.adapter = TCTAdapter(this, tctlist)

            } else {

                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}