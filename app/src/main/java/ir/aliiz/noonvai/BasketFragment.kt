package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_basket.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BasketFragment : Fragment() {
    private val viewModel: BasketViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_basket, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BasketAdapter(plus = {
            viewModel.plus(it.id)
        }, minus = {
            viewModel.minus(it.id)
        })
        recyclerBasketOverview.adapter = adapter
        viewModel.basketItems.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            buttonBasketSubmit.text = it.map { it.count * it.price }.sum().toString()
        }

        viewModel.basketSubmit.observe(viewLifecycleOwner) {
            when (it) {
                is Loadable.Loaded -> findNavController().popBackStack()
            }
        }

        buttonBasketSubmit.setOnClickListener {
            viewModel.clearBasket()
        }
    }
}