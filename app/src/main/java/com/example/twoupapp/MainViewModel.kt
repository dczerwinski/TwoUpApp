package com.example.twoupapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.twoupapp.data.CryptoInfo
import com.example.twoupapp.data.CryptoInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: CryptoInfoRepository
) : ViewModel() {

    companion object {
        private const val DELAY = 30000L
    }

    init {
        getData()
    }

    private val _sortType = MutableLiveData(SortType.BY_NAME_ASC)
    val sortType: LiveData<SortType> = _sortType

    private val _cryptoInfoData = MutableLiveData<Result<CryptoInfo>>()
    val cryptoInfoData: LiveData<Result<CryptoInfo>> = _cryptoInfoData

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val response = repository.getCryptoInfoData()
                val body = response.body()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && body != null) {
                        _cryptoInfoData.postValue(Result.success(body))
                    } else {
                        _cryptoInfoData.postValue(
                            Result.failure(
                                Exception(
                                    response.errorBody().toString()
                                )
                            )
                        )
                    }
                }
                delay(DELAY)
            }
        }
    }

    fun onSortTypeChoose(sortType: SortType) {
        _sortType.postValue(sortType)
    }

    class Factory(
        private val repository: CryptoInfoRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
