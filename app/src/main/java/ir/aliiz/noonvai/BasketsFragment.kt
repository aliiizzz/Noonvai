package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_baskets.*
import org.koin.android.viewmodel.ext.android.viewModel

class BasketsFragment : Fragment() {
    private val basketViewModel: BasketViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_baskets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BasketsAdapter()
        recyclerBasketsItems.adapter = adapter
        basketViewModel.allBaskets.observe(viewLifecycleOwner) {
            when(it) {
                is Loadable.Loaded -> {
                    adapter.items = it.data
                    adapter.notifyDataSetChanged()
                }
            }
        }

        basketViewModel.getAllBaskets()
    }
}