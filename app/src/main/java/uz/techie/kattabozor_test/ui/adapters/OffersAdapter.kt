package uz.techie.kattabozor_test.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import uz.techie.kattabozor_test.databinding.ItemOfferBinding
import uz.techie.kattabozor_test.models.Offer
import uz.usoft.merchapp.utils.extentions.inflater
import uz.usoft.merchapp.utils.extentions.invoke

class OffersAdapter : ListAdapter<Offer, OffersAdapter.OffersViewHolder>(differ) {

    private var itemClickListener: (Offer) -> Unit = {}
    fun setItemClickListener(listener: (Offer) -> Unit) {
        itemClickListener = listener
    }

    inner class OffersViewHolder(private val binding: ItemOfferBinding) : ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            binding {
                root.setOnClickListener { itemClickListener(offer) }
                flag.load(offer.image.url)
                name.text = offer.name
                brand.text = offer.brand
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        return OffersViewHolder(ItemOfferBinding.inflate(parent.inflater))
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private val differ = object : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem == newItem
    }
}