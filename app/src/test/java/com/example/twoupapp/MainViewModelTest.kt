package com.example.twoupapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.twoupapp.data.CryptoInfoRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private val repository: CryptoInfoRepository = mock()
    private val observer: Observer<SortType> = mock()

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
        viewModel.sortType.observeForever(observer)
    }

    @Test
    fun `sortType should return selected type`() {
        val expectedType = SortType.BY_QUANTITY_DESC

        viewModel.onSortTypeChoose(expectedType)

        val captor = ArgumentCaptor.forClass(SortType::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertThat(expectedType, `is`(value))
        }
    }
}
