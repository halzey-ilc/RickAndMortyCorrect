package com.example.rickandmortycorrect.presentation.episode.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortycorrect.R
import com.example.rickandmortycorrect.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortycorrect.domain.model.EpisodeDetail
import com.example.rickandmortycorrect.presentation.episode.viewmodel.EpisodeDetailViewModel
import com.example.rickandmortycorrect.presentation.location.adapter.LocationDetailAdapter
import com.example.rickandmortycorrect.utils.CalculateWindowSize
import com.example.rickandmortycorrect.utils.ItemClickListener
import com.example.rickandmortycorrect.utils.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!
    private val episodeDetailArgs: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: EpisodeDetailViewModel by viewModels()
    lateinit var withWindow: WindowSizeClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        withWindow = CalculateWindowSize(requireActivity()).calculateCurrentHeightSize()
        binding.lifecycleOwner = viewLifecycleOwner
        val episodeId = episodeDetailArgs.episodeId
        binding.viewModel = viewModel

        readyAdapter()
        binding.imageButton.setOnClickListener{
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun readyAdapter() {
        val adapter = LocationDetailAdapter(
            ItemClickListener{characterId ->
                navigateToCharacterDetail(characterId)
            }
        )
        binding.recyclerView.adapter = adapter
        val spanCount = if (withWindow ==WindowSizeClass.EXPANDED) 3 else 2
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
    }

    private fun navigateToCharacterDetail(characterId: Int) {
        val action = EpisodeDetailFragmentDirections.actionToCharacterDetailFragment(
            characterId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}