package com.swastik.wallpaperadminpanel.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Activitys.FinalActivity
import com.swastik.wallpaperadminpanel.R
import com.swastik.wallpaperapp.Model.CatModel

class CatViewAdapter(val requireContext: Context, private val catlist: ArrayList<CatModel>) :
    RecyclerView.Adapter<CatViewAdapter.mviewholder>() {

    private val db = FirebaseFirestore.getInstance()

    class mviewholder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.Cat_image)
        val text = view.findViewById<TextView>(R.id.Cat_text)
        val delete = view.findViewById<ImageView>(R.id.Cat_Delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mviewholder {

        val itemView = LayoutInflater.from(requireContext).inflate(R.layout.item_cat, parent, false)

        return mviewholder(itemView)
    }

    override fun onBindViewHolder(holder: mviewholder, position: Int) {

        val currentpossition = catlist[position]

        Glide.with(requireContext).load(currentpossition.link).into(holder.image)
        holder.text.text = currentpossition.name

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext,FinalActivity::class.java)
            intent.putExtra("name",currentpossition.name)
            intent.putExtra("uid",currentpossition.id)
            requireContext.startActivity(intent)
        }


        holder.delete.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("Are you sure you want to delete?")
            dialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialogInterface, i ->

                db.collection("categories").document(currentpossition.id).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext,
                                "Deleted Succsesfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                requireContext,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        dialogInterface.dismiss()
                    }
            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            dialog.show()


        }


    }

    override fun getItemCount(): Int {

        return catlist.size

    }
}