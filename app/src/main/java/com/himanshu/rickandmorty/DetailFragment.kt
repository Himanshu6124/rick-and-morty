package com.himanshu.rickandmorty

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
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character : Character = args.character

        binding.apply {
            nameTextView.text = character.name
            statusTextView.text = getString(R.string.status, character.status)
            speciesTextView.text = getString(R.string.species, character.species)
            genderTextView.text = getString(R.string.gender, character.gender)
            originTextView.text = getString(R.string.origin, character.origin.name)
            locationTextView.text = getString(R.string.location, character.location.name)
            Glide.with(this@DetailFragment).load(character.image).into(imageView)
        }
    }
}