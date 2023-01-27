package llc.amplitudo.amplitudo_akademija.ui.add_task

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import llc.amplitudo.amplitudo_akademija.R
import llc.amplitudo.amplitudo_akademija.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding get() = _binding!!

    private val viewModel: AddTaskViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDescriptionTextField()
        handleAddTask()
    }

    private fun validateDescriptionTextField() {
        binding.taskDescriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addTaskButton.isEnabled = s.toString().isNotEmpty()
            }
        })
    }

    private fun handleAddTask() {
        binding.addTaskButton.setOnClickListener {
            Toast.makeText(
                this@AddTaskFragment.context,
                getString(R.string.success_add_task),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
    }
}