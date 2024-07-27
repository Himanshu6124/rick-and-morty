package com.himanshu.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.himanshu.rickandmorty.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
//    private val args: DetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)


//        val character = args.character
//
//        Glide.with(this).load(character.image).into(binding.characterImage)
//        binding.characterName.text = character.name
//        binding.characterStatus.text = character.status
//        binding.characterSpecies.text = character.species
//        binding.characterGender.text = character.gender
//        binding.characterOrigin.text = character.origin.name
//        binding.characterLocation.text = character.location.name


        return binding.root
    }


}