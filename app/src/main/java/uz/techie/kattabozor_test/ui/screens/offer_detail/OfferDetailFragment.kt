package uz.techie.kattabozor_test.ui.screens.offer_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import uz.techie.kattabozor_test.R
import uz.techie.kattabozor_test.databinding.ScreenOfferDetailBinding
import uz.techie.kattabozor_test.ui.screens.offers.OffersViewModel
import uz.usoft.merchapp.utils.extentions.collectLatestWithLifecycle
import uz.usoft.merchapp.utils.extentions.finishFragment
import uz.usoft.merchapp.utils.extentions.invoke

@AndroidEntryPoint
class OfferDetailFragment : Fragment(R.layout.screen_offer_detail) {
    private val binding by viewBinding(ScreenOfferDetailBinding::bind)
    private val viewModel by navGraphViewModels<OffersViewModel>(R.id.offersFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { finishFragment() }
        viewModel.selectedOffer.filterNotNull().collectLatestWithLifecycle(lifecycle) {
            binding {
                image.load(it.image.url)
                name.text = it.name
                brand.text = it.brand
                category.text = it.category
                merchant.text = it.merchant
                attributes.text = it.attributes.map { "${it.value} ${it.name}" }.joinToString(",\n")
            }
        }
    }
}