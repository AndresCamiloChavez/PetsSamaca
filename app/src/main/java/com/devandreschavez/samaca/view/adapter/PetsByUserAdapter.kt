package com.devandreschavez.samaca.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.databinding.UserpetreportBinding

class PetsByUserAdapter(private val listPetsByUser: List<Pet>, private val listener: onPetUserClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface onPetUserClickListener{
        fun onReportPetUser(userId: String, petId: String, img: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PetsUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.userpetreport, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PetsUserViewHolder -> holder.bind(listPetsByUser[position], position)
        }
    }

    override fun getItemCount(): Int = listPetsByUser.size

    inner class PetsUserViewHolder(itemView: View):BaseViewHolder<Pet>(itemView){
        private val binding = UserpetreportBinding.bind(itemView)
        override fun bind(item: Pet, position: Int) {
            binding.btnUserPetReport.setOnClickListener{
                listener.onReportPetUser(item.userId, item.id, item.pictureAnimal)
            }
            binding.chipDatePetReport.text = item.date
            binding.descriptioUserPetReport.text = item.description
            binding.tvNamePetReportUser.text = item.namePet
            Glide.with(itemView.context).load(item.pictureAnimal).into(binding.imgUserPetReport)

        }

    }
}