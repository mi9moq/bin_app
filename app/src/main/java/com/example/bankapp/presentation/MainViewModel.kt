package com.example.bankapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase : LoadDataUseCase
) : ViewModel() {

    private val _cardInfo: MutableStateFlow<BinInfo?> = MutableStateFlow(null)
    val cardInfo: StateFlow<BinInfo?>
        get() = _cardInfo

    fun loadCardInfo(inputBin: String?){
        val bin = parseInput(inputBin)
        viewModelScope.launch {
            _cardInfo.value = loadDataUseCase.loadData(bin)
        }
    }

    private fun parseInput(input: String?) = input?.trim() ?: ""
}