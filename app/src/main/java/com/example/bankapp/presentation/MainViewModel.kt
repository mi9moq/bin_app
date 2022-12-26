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
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    private val _cardInfo: MutableStateFlow<BinInfo?> = MutableStateFlow(null)
    val cardInfo: StateFlow<BinInfo?>
        get() = _cardInfo

    private val _errorInputBin: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorInputBin: StateFlow<Boolean>
        get() = _errorInputBin

    private val _binArray: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet())
    val binList: StateFlow<Set<String>>
        get() = _binArray

    fun loadCardInfo(inputBin: String?) {
        val bin = parseInput(inputBin)
        val fieldsValid = validateInput(bin)
        if (fieldsValid) {
            viewModelScope.launch {
                _cardInfo.value = loadDataUseCase.loadData(bin)
                _binArray.value += bin
            }
        }
    }

    private fun parseInput(input: String?) = input?.trim() ?: ""

    private fun validateInput(bin: String): Boolean {
        var result = true
        if (bin.isBlank()) {
            _errorInputBin.value = true
            result = false
        }
        return result
    }

    fun resetErrorInput() {
        _errorInputBin.value = false
    }
}