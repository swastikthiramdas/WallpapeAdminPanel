package com.swastik.wallpaperadminpanel.Adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.swastik.wallpaperadminpanel.R
import com.swastik.wallpaperapp.Model.TCTModel


class TCTAdapter(val requireContext: Context, private val tctlist: ArrayList<TCTModel>) :
    RecyclerView.Adapter<TCTAdapter.viewmodel>() {

    val db = FirebaseFirestore.getInstance()

    class viewmodel(view: View) : RecyclerView.ViewHolder(view) {

        val colore = view.findViewById<CardView>(R.id.tct_colore)
        val delete = view.findViewById<ImageView>(R.id.tct_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewmodel {

        val itemview = LayoutInflater.from(requireContext).inflate(R.layout.item_tct, parent, false)

        return viewmodel(itemview)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: viewmodel, position: Int) {

        val currentpossition = tctlist[position]

        holder.colore.setCardBackgroundColor(Color.parseColor(currentpossition.colore))
        holder.delete.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("Are you sure you want to delete?")
            dialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialogInterface, i ->

                db.collection("thecoloreton").document(currentpossition.id).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext, "Deleted Succsesfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(requireContext, "Something went wrong", Toast.LENGTH_SHORT)
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
        return tctlist.size
    }
}