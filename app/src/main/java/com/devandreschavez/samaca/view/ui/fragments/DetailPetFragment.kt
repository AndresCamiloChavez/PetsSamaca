package com.devandreschavez.samaca.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.databinding.FragmentDetailPetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DetailPetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailPetBinding
    private val args:DetailPetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailPetBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.petUser.apply {
            Glide.with(view.context).load(pet.pictureAnimal).into(binding.imgDetailPet)
            binding.tvNamePetDetail.text = pet.namePet
            binding.chipDatePetDetail.text = pet.date
            binding.chipSectorDetail.text = pet.sector
            binding.tvDescriptionPetDetail.text = pet.description
            binding.tvEmailDetail.text = user.email
            binding.tvNameUserDetail.text = user.fullName
            binding.tvPhoneUserDetail.text = user.phone

        }
    }
}