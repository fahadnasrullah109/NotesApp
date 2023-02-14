package com.example.journel.app.presentation.note.add

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.journel.app.R
import com.example.journel.app.Utils
import com.example.journel.app.data.local.models.Mood
import com.example.journel.app.databinding.FragmentAddNoteBinding
import com.example.journel.app.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val binding: FragmentAddNoteBinding by viewBinding()
    private val viewModel: AddNoteVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveNoteBtn.setOnClickListener {
            viewModel.saveNote(binding.noteEt.text.toString(), getSelectedMood())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.noteErrorTv.hide()
                    when (uiState) {
                        is AddNoteUIState.SaveNoteSuccess -> {
                            binding.progressBar.hide()
                            binding.saveNoteBtn.enable()
                            sendSuccessAndDismiss()
                        }
                        is AddNoteUIState.LoadingState -> {
                            binding.progressBar.show()
                            binding.saveNoteBtn.disable()
                        }
                        is AddNoteUIState.ErrorState -> showToast(
                            uiState.message ?: getString(R.string.generic_error)
                        )
                        AddNoteUIState.IdleState -> {}
                        is AddNoteUIState.ValidationErrorState -> {
                            binding.noteErrorTv.show()
                            binding.noteErrorTv.text = uiState.message
                        }
                    }
                }
            }
        }
    }

    private fun getSelectedMood() = when {
        binding.radioHappyBtn.isChecked -> Mood.HAPPY
        binding.radioNormalBtn.isChecked -> Mood.NEUTRAL
        binding.radioBadBtn.isChecked -> Mood.SAD
        else -> Mood.NOTHING
    }

    private fun sendSuccessAndDismiss() {
        setFragmentResult(
            Utils.KEY_IS_NOTE_ADDED, bundleOf(Utils.KEY_IS_NOTE_ADDED to true)
        )
        findNavController().popBackStack()
    }
}