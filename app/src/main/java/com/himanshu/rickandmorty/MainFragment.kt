package com.himanshu.rickandmorty

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.himanshu.rickandmorty.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val TAG = "MainFragment"

    private lateinit var characterViewModel: CharacterViewModel

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cache = RetrofitInstance.provideCache(requireContext())
        val client = RetrofitInstance.provideOkHttpClient(requireContext(), cache)
        val retrofit = RetrofitInstance.provideRetrofit(client)
        val service = RetrofitInstance.provideApiService(retrofit)
        val repo = CharacterRepository(service)
        characterViewModel =
            ViewModelProvider(this, CharacterViewModelFactory(repo))[CharacterViewModel::class.java]

        addSubscribers()
        addListeners()
    }

    private fun addListeners() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { characterViewModel.searchCharacters(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { characterViewModel.searchCharacters(it) }
                return true
            }
        })

        binding.getNextList.setOnClickListener {
            if (characterViewModel.currentPage == 42) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.last_page_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                characterViewModel.currentPage++
                characterViewModel.getCharacters(characterViewModel.currentPage)
            }
        }

        binding.getPrevList.setOnClickListener {
            if (characterViewModel.currentPage == 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.first_page_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                characterViewModel.currentPage--
                characterViewModel.getCharacters(characterViewModel.currentPage)
            }
        }
    }


    private fun addSubscribers() {
        characterViewModel.characters.observe(viewLifecycleOwner) {
            Log.i(TAG, "Response is $it")
            val adapter = CharacterAdapter(it.results) { character ->
                Log.i(TAG, "character clicked ${character.name}")
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(character)
                findNavController().navigate(action)
            }
            binding.recyclerView.layoutManager = GridLayoutManager(
                activity,
                2
            )
            binding.recyclerView.adapter = adapter
        }

        characterViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) binding.loader.visibility = View.VISIBLE
            else binding.loader.visibility = View.GONE
        }

        characterViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }

//        characterViewModel.currentPage.observe(viewLifecycleOwner){ pageNumber->
//            binding.pageNumber.text = getString(R.string.page_42, pageNumber.toString())
//        }
    }
}