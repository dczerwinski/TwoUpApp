package com.example.twoupapp

import android.animation.LayoutTransition
import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twoupapp.databinding.ItemCryptoInfoBinding

class CryptoInfoViewHolder(
    private val binding: ItemCryptoInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    fun bind(item: CryptoInfoAdapter.Item) {
        val context = binding.root.context

        binding.name.text = context.getString(R.string.item_name, item.data.name)
        binding.symbol.text = context.getString(R.string.item_symbol, item.data.symbol)
        binding.price.text = context.getString(R.string.item_price, item.data.price_usd)
        binding.percentChangeLastHour.text =
            context.getString(R.string.item_percent_change_last_hour, item.data.percent_change_1h)
        binding.percentChangeLastDay.text =
            context.getString(R.string.item_percent_change_last_day, item.data.percent_change_24h)
        binding.quantity.text = context.getString(R.string.item_quantity, item.data.volume24)

        when (item.trend) {
            CryptoInfoAdapter.Trend.GROWING -> {
                changeTrendIcon(-90f, R.color.trend_growing, context)
            }
            CryptoInfoAdapter.Trend.NO_CHANGE -> {
                changeTrendIcon(0f, R.color.trend_no_change, context)
            }
            CryptoInfoAdapter.Trend.DECREASING -> {
                changeTrendIcon(90f, R.color.trend_decreasing, context)
            }
        }
    }

    private fun changeTrendIcon(rotation: Float, @ColorRes colorResId: Int, context: Context) {
        binding.trendIcon.rotation = rotation
        ImageViewCompat.setImageTintList(
            binding.trendIcon, ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    colorResId
                )
            )
        )
    }
}
