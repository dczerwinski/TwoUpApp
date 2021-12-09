package com.example.twoupapp

import androidx.annotation.StringRes

enum class SortType(
    val isAscending: Boolean,
    @StringRes val stringResId: Int
) {
    BY_NAME_ASC(true, R.string.name_sort_asc),
    BY_NAME_DESC(false, R.string.name_sort_desc),
    BY_QUANTITY_ASC(true, R.string.quantity_sort_asc),
    BY_QUANTITY_DESC(true, R.string.quantity_sort_desc),
    BY_PRICE_CHANGE_IN_DAY_ASC(true, R.string.percent_price_change_sort_asc),
    BY_PRICE_CHANGE_IN_DAY_DESC(false, R.string.percent_price_change_sort_desc)
}
