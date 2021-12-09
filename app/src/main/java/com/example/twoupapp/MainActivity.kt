package com.example.twoupapp

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twoupapp.data.CryptoInfoRepository
import com.example.twoupapp.databinding.ActivityMainBinding
import com.example.twoupapp.network.Endpoint
import com.example.twoupapp.network.ServiceBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var isAscending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModel.Factory(CryptoInfoRepository(ServiceBuilder.buildService(Endpoint::class.java)))
            )[MainViewModel::class.java]

        setUpUI()
    }

    private fun setUpUI() {
        val cryptoInfoAdapter = CryptoInfoAdapter()

        viewModel.sortType.observe(
            this,
            { sortType ->
                binding.currentSortType.text = getString(sortType.stringResId)
                flipImage(sortType.isAscending)
                cryptoInfoAdapter.sort(sortType)
            }
        )

        binding.menuContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.menuContainer.setOnClickListener {
            showMenu(it, R.menu.sort_menu)
        }

        binding.list.apply {
            adapter = cryptoInfoAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        viewModel.cryptoInfoData.observe(this, { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    cryptoInfoAdapter.setData(it.data)
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.error_getting_data, result.exceptionOrNull()?.toString()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.name_sort_asc -> {
                    viewModel.onSortTypeChoose(SortType.BY_NAME_ASC)
                    true
                }
                R.id.name_sort_desc -> {
                    viewModel.onSortTypeChoose(SortType.BY_NAME_DESC)
                    true
                }
                R.id.quantity_sort_asc -> {
                    viewModel.onSortTypeChoose(SortType.BY_QUANTITY_ASC)
                    true
                }
                R.id.quantity_sort_desc -> {
                    viewModel.onSortTypeChoose(SortType.BY_QUANTITY_DESC)
                    true
                }
                R.id.percent_price_change_sort_asc -> {
                    viewModel.onSortTypeChoose(SortType.BY_PRICE_CHANGE_IN_DAY_ASC)
                    true
                }
                R.id.percent_price_change_sort_desc -> {
                    viewModel.onSortTypeChoose(SortType.BY_PRICE_CHANGE_IN_DAY_DESC)
                    true
                }
                else -> true
            }
        }
        popup.show()
    }

    private fun flipImage(isAscending: Boolean) {
        if (this.isAscending != isAscending) {
            this.isAscending = isAscending
            if (isAscending) {
                binding.sortIcon.scaleY = 1f
            } else {
                binding.sortIcon.scaleY = -1f
            }
        }
    }
}
