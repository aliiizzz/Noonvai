package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.basket_mini.view.*
import kotlinx.android.synthetic.main.fragment_vitrin.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class VitrinFragment : Fragment() {

    private val breadViewModel: BreadViewModel by viewModel()

    private val basketViewModel: BasketViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vitrin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BreadAdapter( plusClicked = {
            basketViewModel.plus(it.id)
        }, minusClicked ={
            basketViewModel.minus(it.id)
        })
        recyclerVitrinItems.adapter = adapter
        basketViewModel.basketItems.observe(viewLifecycleOwner) {
            adapter.basket = it
            adapter.notifyDataSetChanged()
            layoutVitrinBasket.textBasketMiniCount.text = it.map { it.count }.sum().toString()
        }
        breadViewModel.items.observe(viewLifecycleOwner) {
            when (it) {
                is Loadable.Loaded -> {
                    adapter.items = it.data
                    adapter.notifyDataSetChanged()
                }
            }
        }
        breadViewModel.checkItems()
        buttonVitrinSettings.setOnClickListener {
            findNavController().navigate(VitrinFragmentDirections.actionVitrinToSettings())
        }

        buttonVitrinSubmit.setOnClickListener {
            findNavController().navigate(VitrinFragmentDirections.actionVitrinToBasket())
        }
    }
}