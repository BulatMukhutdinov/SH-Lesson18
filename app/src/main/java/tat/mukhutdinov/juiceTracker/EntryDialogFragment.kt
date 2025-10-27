package tat.mukhutdinov.juiceTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tat.mukhutdinov.juiceTracker.data.JuiceColor
import tat.mukhutdinov.juiceTracker.databinding.FragmentEntryDialogBinding
import tat.mukhutdinov.juiceTracker.ui.AppViewModelProvider
import tat.mukhutdinov.juiceTracker.ui.EntryViewModel

class EntryDialogFragment : BottomSheetDialogFragment() {
    private val entryViewModel by viewModels<EntryViewModel> { AppViewModelProvider.Factory }
    var selectedColor: JuiceColor = JuiceColor.Red

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)
        val args: EntryDialogFragmentArgs by navArgs()
        val juiceId = args.itemId

        binding.saveButton.setOnClickListener {
            entryViewModel.saveJuice(
                id = juiceId,
                name = binding.name.text.toString(),
                description = binding.description.text.toString(),
                color = selectedColor.name,
                rating = binding.ratingBar.rating.toInt()
            )

            dismiss()
        }
    }
}