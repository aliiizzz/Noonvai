package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_bread.*
import org.koin.android.viewmodel.ext.android.viewModel

class BreadFragment : Fragment() {
    private val breadViewModel: BreadViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_bread, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BreadVerticalAdapter(edit = {
            findNavController().navigate(BreadFragmentDirections.actionBreadToEdit(it.id))
        }, delete = {
            breadViewModel.delete(it.id)
        })
        recyclerBreadItems.adapter = adapter
        breadViewModel.items.observe(viewLifecycleOwner) {
            when(it) {
                is Loadable.Loaded -> {
                    adapter.items = it.data
                    adapter.notifyDataSetChanged()
                }
            }
        }
        breadViewModel.checkItems()
        floatingBreadAdd.setOnClickListener {
            findNavController().navigate(BreadFragmentDirections.actionBreadToEdit(-1))
        }
    }
}