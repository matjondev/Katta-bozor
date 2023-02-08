package uz.techie.kattabozor_test.ui.screens.offers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import uz.techie.kattabozor_test.R
import uz.techie.kattabozor_test.databinding.ScreenOffersBinding
import uz.techie.kattabozor_test.models.Offer
import uz.techie.kattabozor_test.ui.adapters.OffersAdapter
import uz.usoft.merchapp.utils.extentions.collectLatestWithLifecycle
import uz.usoft.merchapp.utils.extentions.invoke
import uz.usoft.merchapp.utils.extentions.navController
import uz.usoft.merchapp.utils.extentions.navigate

@AndroidEntryPoint
class OffersFragment : Fragment(R.layout.screen_offers) {
    private val binding by viewBinding(ScreenOffersBinding::bind)
    private val viewModel by navGraphViewModels<OffersViewModel>(R.id.offersFragment) { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding {
            val uiState = viewModel.uiState
            bindToolbar()
            bindLoading(uiState.map { it.isLoading })
            binding.bindOffers(uiState.map { it.offers })
        }
    }

    private fun ScreenOffersBinding.bindToolbar() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    viewModel.execute(RefreshOffersAction)
                    true
                }
                else -> false
            }
        }
    }

    private fun ScreenOffersBinding.bindLoading(isLoading: Flow<Boolean>) {
        isLoading.distinctUntilChanged().collectLatestWithLifecycle(lifecycle) {
            progressCircular.isVisible = it
        }
    }

    private fun ScreenOffersBinding.bindOffers(offers: Flow<List<Offer>>) {
        val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val adapter = OffersAdapter()

        recycler.adapter = adapter
        recycler.addItemDecoration(decoration)

        offers.distinctUntilChanged().collectLatestWithLifecycle(lifecycle) {
            adapter.submitList(it)
        }

        adapter.setItemClickListener {
            viewModel.execute(SelectOfferAction(it))
            OffersFragmentDirections.actionOffersFragmentToOfferDetailFragment().navigate()
        }
    }
}