package com.himanshu.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.himanshu.rickandmorty.databinding.FragmentDetailBinding
import com.himanshu.rickandmorty.model.Character

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView(args.character)
    }

    private fun initializeView(character: Character) {
        binding.apply {
            nameTextView.text = character.name
            statusTextView.text = character.status
            speciesTextView.text = character.species
            genderTextView.text = character.gender
            originTextView.text = character.origin.name
            locationTextView.text = character.location.name
            Glide.with(this@DetailFragment).load(character.image).into(imageView)
        }
    }

    /* to perform cleanup */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}