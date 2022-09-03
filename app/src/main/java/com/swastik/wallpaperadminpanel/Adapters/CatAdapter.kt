package com.swastik.wallpaperadminpanel.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.Model.BomModel
import com.swastik.wallpaperadminpanel.R


class CatAdapter(val requiredcontext: Context, private val list: ArrayList<BomModel>, val uid: String?) :
    RecyclerView.Adapter<CatAdapter.viewholder>() {

    private val db = FirebaseFirestore.getInstance()

    class viewholder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.item_image)
        val delete = view.findViewById<ImageView>(R.id.final_Delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val itemview =
            LayoutInflater.from(requiredcontext).inflate(R.layout.item_wallpaper, parent, false)

        return viewholder(itemview)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentitem = list[position]

        Glide.with(requiredcontext).load(currentitem.link).into(holder.image)

        holder.delete.setOnClickListener {

            val dialog = AlertDialog.Builder(requiredcontext)
            dialog.setMessage("Are you sure you want to delete?")
            dialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialogInterface, i ->

                db.collection("categories").document(uid!!).collection("wallpaper").document(currentitem.id)
                    .delete().addOnCompleteListener{
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requiredcontext,
                                "Deleted Succsesfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                requiredcontext,
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

        return list.size
    }
}