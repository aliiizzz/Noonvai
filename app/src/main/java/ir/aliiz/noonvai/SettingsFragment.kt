package ir.aliiz.noonvai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSettingsAdd.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToBread())
        }

        buttonSettingsBasket.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToBasket())
        }
    }
}