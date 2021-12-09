package com.example.twoupapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twoupapp.data.Data
import com.example.twoupapp.databinding.ItemCryptoInfoBinding

@SuppressLint("NotifyDataSetChanged")
class CryptoInfoAdapter : RecyclerView.Adapter<CryptoInfoViewHolder>() {

    private val items = ArrayList<Item>()
    private var currentSortType = SortType.BY_NAME_ASC
    private val priceHashMap = HashMap<String, Double>()

    fun setData(dataList: List<Data>) {
        items.clear()
        dataList.forEach { data ->
            val oldPrice = priceHashMap[data.name]
            val trend = if (oldPrice != null) {
                val diff = data.price_usd.toDouble() - oldPrice
                when {
                    diff == 0.0 -> {
                        Trend.NO_CHANGE
                    }
                    diff > 0.0 -> {
                        Trend.GROWING
                    }
                    else -> {
                        Trend.DECREASING
                    }
                }
            } else {
                Trend.NO_CHANGE
            }
            items.add(Item(data, trend))
            priceHashMap[data.name] = data.price_usd.toDouble()
        }
        sort(currentSortType)
    }

    fun sort(sortType: SortType) {
        currentSortType = sortType
        when (sortType) {
            SortType.BY_NAME_ASC -> items.sortBy { it.data.name }
            SortType.BY_NAME_DESC -> items.sortByDescending { it.data.name }
            SortType.BY_QUANTITY_ASC -> items.sortBy { it.data.volume24 }
            SortType.BY_QUANTITY_DESC -> items.sortByDescending { it.data.volume24 }
            SortType.BY_PRICE_CHANGE_IN_DAY_ASC -> items.sortBy { it.data.percent_change_24h.toDouble() }
            SortType.BY_PRICE_CHANGE_IN_DAY_DESC -> items.sortByDescending { it.data.percent_change_24h.toDouble() }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoInfoViewHolder {
        val binding =
            ItemCryptoInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CryptoInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoInfoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    data class Item(
        val data: Data,
        val trend: Trend
    )

    enum class Trend {
        GROWING,
        NO_CHANGE,
        DECREASING
    }
}
