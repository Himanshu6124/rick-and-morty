package com.himanshu.rickandmorty.ui.fragments

import android.os.Bundle
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
import com.himanshu.rickandmorty.repository.CharacterRepository
import com.himanshu.rickandmorty.viewmodel.CharacterViewModel
import com.himanshu.rickandmorty.viewmodel.CharacterViewModelFactory
import com.himanshu.rickandmorty.R
import com.himanshu.rickandmorty.network.RetrofitInstance
import com.himanshu.rickandmorty.network.RickAndMortyApiService
import com.himanshu.rickandmorty.ui.adapters.CharacterAdapter
import com.himanshu.rickandmorty.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var adapter: CharacterAdapter? = null
    private val service: RickAndMortyApiService by lazy {
        RetrofitInstance.provideApiService(
            requireContext()
        )
    }

    private val characterViewModel: CharacterViewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModelFactory(
                CharacterRepository(service)
            )
        )[CharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSubscribers()
        setupRecyclerView()
        addListeners()
    }

    /* to add listeners of ui events */
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

        /* to get characters of next page */
        binding.getNextList.setOnClickListener {
            if (characterViewModel.currentPage.value == CharacterViewModel.TOTAL_PAGES) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.last_page_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                characterViewModel.getNextPage()
            }
        }

        /* to get characters of previous page */
        binding.getPrevList.setOnClickListener {
            if (characterViewModel.currentPage.value == 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.first_page_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                characterViewModel.getPrevPage()
            }
        }
    }

    /* to set up recycler view and show dat from remote */
    private fun setupRecyclerView() {
        adapter = CharacterAdapter { character ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(character)
            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.adapter = adapter
    }

    /* to add listeners of ui events */
    private fun addSubscribers() {
        characterViewModel.characters.observe(viewLifecycleOwner) {
            adapter?.submitList(it.results)
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

        characterViewModel.currentPage.observe(viewLifecycleOwner) { pageNumber ->
            binding.pageNumber.text = getString(R.string.page_42, pageNumber.toString())
        }
    }

    /* to perform cleanup */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}