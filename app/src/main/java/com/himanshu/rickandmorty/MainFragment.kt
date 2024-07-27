package com.himanshu.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.rickandmorty.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val TAG = "MainFragment"

    private val characterRepository =
        CharacterRepository(RetrofitInstance.apiService)

    private val characterViewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModelFactory(
                characterRepository
            )
        )[CharacterViewModel::class.java]
    }
    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        characterViewModel.characters.observe(viewLifecycleOwner) {
            Log.i(TAG, "Response is $it")
            val adapter = CharacterAdapter(it.results){character ->
                Log.i(TAG,"character clicked ${character.name}")
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(character)
                findNavController().navigate(action)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(activity,
                RecyclerView.VERTICAL,false)
            binding.recyclerView.adapter = adapter

        }

        characterViewModel.isLoading.observe(viewLifecycleOwner){isLoading ->
            if(isLoading) binding.loader.visibility = View.VISIBLE
            else binding.loader.visibility = View.GONE
        }

        characterViewModel.getCharacters(characterViewModel.currentPage)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { characterViewModel.searchCharacters(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.getNextList.setOnClickListener {
            characterViewModel.currentPage++
            characterViewModel.getCharacters(characterViewModel.currentPage)
        }

        binding.getPrevList.setOnClickListener {
            if(characterViewModel.currentPage > 1){
                characterViewModel.currentPage--
                characterViewModel.getCharacters(characterViewModel.currentPage)
            }
        }

        return binding.root
    }

}