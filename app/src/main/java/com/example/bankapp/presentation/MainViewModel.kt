package com.example.bankapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.entity.CardInfo
import com.example.bankapp.domain.usecase.GetBinListUseCase
import com.example.bankapp.domain.usecase.LoadDataUseCase
import com.example.bankapp.domain.usecase.SaveBinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val saveBinUseCase: SaveBinUseCase,
    private val getBinListUseCase: GetBinListUseCase
) : ViewModel() {

    private val _cardInfo: MutableStateFlow<CardInfo?> = MutableStateFlow(null)
    val cardInfo: StateFlow<CardInfo?>
        get() = _cardInfo

    private val _errorInputBin: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorInputBin: StateFlow<Boolean>
        get() = _errorInputBin

    private val _binSet: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet())
    val binSet: StateFlow<Set<String>>
        get() = _binSet

    init {
        viewModelScope.launch {
            getBinListUseCase.getBinList().collect{set->
                set.map {
                    _binSet.value+=it.bin
                }
            }
        }
    }

    fun loadCardInfo(inputBin: String?) {
        val bin = parseInput(inputBin)
        val fieldsValid = validateInput(bin)
        if (fieldsValid) {
            viewModelScope.launch {
                _cardInfo.value = loadDataUseCase.loadData(bin)
                val binInfo = BinInfo(bin = bin)
                saveBinUseCase.saveBin(binInfo)
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