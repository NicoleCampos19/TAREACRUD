package emily.jacobo.crudhelpdesk.ui.bandeja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import emily.jacobo.crudhelpdesk.databinding.FragmentBandejaBinding

class BandejaFragment : Fragment() {

    private var _binding: FragmentBandejaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bandejaViewModel =
            ViewModelProvider(this).get(BandejaViewModel::class.java)

        _binding = FragmentBandejaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        bandejaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}