package com.example.projectsound

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_sound.view.*

class AdapterSound(
    private val context : Context,
    private val arrayName : Array<String>,
    private val arrayImage : Array<Int>)  :
    RecyclerView.Adapter<AdapterSound.ViewHolders>(){

    private var places: InterfaceSound

    init {
        places = context as InterfaceSound
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.txtNameSound.text = arrayName[position]
        holder.imgPhoto.setImageResource(arrayImage[position])
        holder.linear.setOnClickListener{
            places.onClickItem(position)
        }
    }

    override fun getItemCount(): Int {
        return arrayName.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        return ViewHolders(
            LayoutInflater.from(context).inflate(
                R.layout.custom_list_sound,
                parent,
                false
            )
        )
    }

    internal interface InterfaceSound {
        fun onClickItem(num : Int)
    }

    class ViewHolders (view: View) : RecyclerView.ViewHolder(view) {
        val txtNameSound : TextView = view.txtNameSound
        val imgPhoto : ImageView = view.imgPhoto
        val linear: RelativeLayout = view.main_linear
    }
}