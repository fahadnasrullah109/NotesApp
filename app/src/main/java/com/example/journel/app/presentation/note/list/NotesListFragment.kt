package com.example.journel.app.presentation.note.list

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journel.app.R
import com.example.journel.app.Utils
import com.example.journel.app.databinding.FragmentNotesListBinding
import com.example.journel.app.extensions.hide
import com.example.journel.app.extensions.show
import com.example.journel.app.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.fragment_notes_list) {
    private val binding: FragmentNotesListBinding by viewBinding()
    private val listAdapter = NotesAdapter()
    private val viewModel: NotesListVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Utils.KEY_IS_NOTE_ADDED) { key, bundle ->
            val isNoteAdded = bundle.getBoolean(key)
            if (isNoteAdded && isVisible) {
                viewModel.getNotes()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtn.setOnClickListener {
            addNewNote()
        }
        binding.charactersRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    hideProgress()
                    when (uiState) {
                        NotesUIState.LoadingState -> {
                            binding.noDataLayout.root.hide()
                            showProgress()
                        }
                        is NotesUIState.SuccessState -> {
                            if (uiState.notes.isEmpty()) {
                                binding.noDataLayout.root.show()
                            } else {
                                binding.noDataLayout.root.hide()
                            }
                            listAdapter.replaceList(uiState.notes)
                        }
                        is NotesUIState.ErrorState -> {
                            showToast(uiState.message ?: getString(R.string.generic_error))
                        }
                    }
                }
            }
        }
    }

    private fun showProgress() = binding.progressBar.show()

    private fun hideProgress() = binding.progressBar.hide()

    private fun addNewNote() = findNavController().navigate(
        R.id.action_list_to_LoginFragment
    )
}