package com.example.rickandmortycorrect.presentation.character.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycorrect.R
import com.example.rickandmortycorrect.databinding.DialogViewBinding
import com.example.rickandmortycorrect.databinding.FragmentCharacterListBinding
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.presentation.character.adapter.CharacterAdapter
import com.example.rickandmortycorrect.presentation.character.viewmodel.CharacterViewModel
import com.example.rickandmortycorrect.presentation.character.viewmodel.states.ListType
import com.example.rickandmortycorrect.utils.CalculateWindowSize
import com.example.rickandmortycorrect.utils.ItemLongClickListener
import com.example.rickandmortycorrect.utils.WindowSizeClass
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var widthWindow: WindowSizeClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]
        widthWindow = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        readyCharacterAdapter()
        setCharacterListLayoutManager()
        viewModel.checkIfTheFilterHasBeenApplied()
        getListData()
        binding.refreshBtn.setOnClickListener {
            characterAdapter.retry()
        }

        viewLifecycleOwner.lifecycleScope.launch {

        }

        binding.imgListType.apply {
            this.setOnClickListener {
                viewModel.setLayoutManager()
                setListTypeIcon(this)
                setCharacterListLayoutManager()
            }
        }


        return binding.root
    }

    private fun readyCharacterAdapter() {
        characterAdapter = CharacterAdapter(
            ItemLongClickListener {
                showAlertDialog(it)
            }
        )
        setCharacterListLayoutManager()
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.characterList.adapter = characterAdapter

    }

    private fun setListTypeIcon(imageView: ImageView) {
        val icon = when (viewModel.getListType()) {
            ListType.GridLayout -> R.drawable.grid_list
            else -> R.drawable.ic_list_icon
        }
        imageView.setImageResource(icon)
    }

    private fun setCharacterListLayoutManager() {
        if (viewModel.getListType() == ListType.GridLayout) {

            val spanCount = if (widthWindow == WindowSizeClass.EXPANDED) 3 else 2

            binding.characterList.layoutManager = GridLayoutManager(requireContext(), spanCount)

            characterAdapter.setListType(ListType.GridLayout)
        } else {
            binding.characterList.layoutManager = LinearLayoutManager(requireContext())
            characterAdapter.setListType(ListType.LinearLayout)
        }
    }

    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getListData().collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }


    private fun showAlertDialog(charactersDomain: CharactersDomain) {

        viewModel.getAllFavoriteCharacters()
        val dialogView = DialogViewBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .create()


        val isHasAddedCharacter = viewModel.isHasAddedCharacter(charactersDomain)

        setDialogText(isHasAddedCharacter, dialogView, charactersDomain)

        alertDialog.show()

        dialogView.btnYes.setOnClickListener {

            if (isHasAddedCharacter) {
                viewModel.deleteCharacterFromMyFavoriteList(charactersDomain)
                alertDialog.cancel()

            } else {
                charactersDomain.isFavoriteState(true)
                viewModel.insertMyFavoriteList(charactersDomain)
                alertDialog.cancel()
            }
            showToastMessage()
            viewModel.doneToastMessage()
        }

        dialogView.btnNo.setOnClickListener {
            alertDialog.cancel()
        }

    }

    private fun showToastMessage() {
        if (viewModel.getIsShowToastMessage()) {
            Toast.makeText(
                requireContext(),
                viewModel.getToastMessage(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setDialogText(
        isHasAddedCharacter: Boolean,
        dialogView: DialogViewBinding,
        charactersDomain: CharactersDomain,
    ) {
        if (isHasAddedCharacter) {
            dialogView.txtHeader.text = getString(R.string.dialog_header_remove_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_remove_character_favorite, charactersDomain.name)
        } else {
            dialogView.txtHeader.text = getString(R.string.dialog_header_add_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_add_character_favorite, charactersDomain.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            val action = CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        setListTypeIcon(binding.imgListType)
    }
}