package llc.amplitudo.amplitudo_akademija.ui.all_tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import llc.amplitudo.amplitudo_akademija.data.remote.models.Task
import llc.amplitudo.amplitudo_akademija.databinding.FragmentAllTasksBinding
import llc.amplitudo.amplitudo_akademija.ui.adapters.TaskAdapter

class AllTasksFragment : Fragment() {

    private var _binding: FragmentAllTasksBinding? = null
    private val binding: FragmentAllTasksBinding get() = _binding!!

    private val viewModel: AllTasksViewModel by viewModels()
    private lateinit var tasksRecyclerView: RecyclerView
    private var taskAdapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTasks()
        binding.fab.setOnClickListener {
            navigateToAddTask()
        }
        /**
         * Example how to use Glide!
         */
//        Glide.with(binding.root.context)
//            .load(user.imageUrl)
//            .into(userImage)
//            .onLoadStarted(
//                ContextCompat.getDrawable(
//                    binding.root.context,
//                    R.drawable.user_placeholder
//                )
//            )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingMessageFlow.collectLatest { message ->
                if(message){
                    binding.loading.visibility = View.VISIBLE
                }
                else binding.loading.visibility = View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksSharedFlow.collectLatest { tasks ->
                initTaskRecycler(tasks = tasks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearBinding()
    }

    /**
     * [See reason](https://stackoverflow.com/questions/66119231/is-it-necessary-to-set-viewbinding-to-null-in-fragments-ondestroy)
     */
    private fun clearBinding() {
        _binding = null
    }

    private fun initTaskRecycler(tasks: List<Task>) {
        taskAdapter = TaskAdapter(tasks = tasks, isAllTasks = true)
        tasksRecyclerView = binding.tasksRecyclerView
        tasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AllTasksFragment.context)
            adapter = taskAdapter
        }
    }

    private fun navigateToAddTask() {
        val action = AllTasksFragmentDirections.actionFragmentAllTasksToAddTaskFragment()
        findNavController().navigate(action)
    }
}