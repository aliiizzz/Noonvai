package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_add_bread.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddBreadFragment : Fragment() {

    private val viewmodel: BreadViewModel by viewModel()
    private val args: AddBreadFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_bread, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.checkBread(args.id)
        viewmodel.addBread.observe(viewLifecycleOwner) {
            when(it) {
                is Loadable.Loaded -> findNavController().popBackStack()
            }
        }

        viewmodel.editBread.observe(viewLifecycleOwner) {
            when (it) {
                is Loadable.Loaded -> findNavController().popBackStack()
            }
        }

        viewmodel.bread.observe(viewLifecycleOwner) {
            when(it) {
                is Loadable.Loaded -> {
                    inputAddBreadPrice.setText(it.data.price.toString())
                    inputAddBreadTitle.setText(it.data.title)
                }
            }
        }
        buttonAddBreadSubmit.setOnClickListener {
            if (args.id == -1) {
                viewmodel.add(inputAddBreadTitle.text.toString(), inputAddBreadPrice.text.toString())
            } else {
                viewmodel.edit(inputAddBreadTitle.text.toString(), inputAddBreadPrice.text.toString(), args.id)
            }
        }
    }
}