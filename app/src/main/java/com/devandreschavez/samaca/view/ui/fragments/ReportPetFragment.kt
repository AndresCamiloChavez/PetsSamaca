package com.devandreschavez.samaca.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.databinding.FragmentReportPetBinding
import com.google.android.material.datepicker.MaterialDatePicker

class ReportPetFragment : Fragment(R.layout.fragment_report_pet) {

    private lateinit var binding: FragmentReportPetBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportPetBinding.bind(view)


        setData()

        binding.btnReporterPet.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(childFragmentManager, "Date")
//            Toast.makeText(requireContext(), "Valor ${datePicker.selection}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData() {
        val typePet = listOf("Perro", "Gato", "Otro")
        val adapterPet = ArrayAdapter(requireContext(), R.layout.list_item, typePet)
        (binding.menuType.editText as? AutoCompleteTextView)?.setAdapter(adapterPet)
        val typeSex = listOf("Macho", "Hembra",)
        val adapterSex = ArrayAdapter(requireContext(), R.layout.list_item, typeSex)
        (binding.menuSex.editText as? AutoCompleteTextView)?.setAdapter(adapterSex)

    }

}