package com.devandreschavez.samaca.view.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.databinding.ItemPetBinding

class PetsAdapter(private var listPets: List<Pet>, private val listener: onPetClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {


    interface onPetClickListener{
        fun onItemClick(item: Pet)
        fun onSharePostPet( name: String, description: String, img:String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PetsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent,false ))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PetsViewHolder -> holder.bind(listPets[position], position)
        }
    }
    override fun getItemCount(): Int = listPets.size

    inner class PetsViewHolder(itemView: View): BaseViewHolder<Pet>(itemView) {
        private val binding = ItemPetBinding.bind(itemView)
        override fun bind(item: Pet, position: Int) {
            itemView.setOnClickListener {
                listener.onItemClick(item)
            }

            binding.tvNamePet.text = item.namePet
            binding.tvSector.text = "Última vez en ${item.sector}"
            Glide.with(itemView.context).load(item.pictureAnimal).into(binding.imgPet)
            binding.btnShare.setOnClickListener {
                listener.onSharePostPet(item.namePet, item.description, item.pictureAnimal)
            }
            binding.chipDate.text = item.date.toString()
            binding.tvDescriptionPet.text = item.description
        }
    }
}