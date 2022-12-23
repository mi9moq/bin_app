package com.example.bankapp.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentMainBinding
import com.example.bankapp.domain.entity.BinInfo
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTextChangeListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.cardInfo.collect {
                    setupViews(it)
                }
            }
        }
    }

    private fun setupViews(bin: BinInfo?) {
        with(binding) {
            bin?.let {
                tvScheme.text = it.scheme
                tvType.text = it.type // null
                tvBrand.text = it.brand //null
                tvPrepaid.text = if (it.prepaid != null && it.prepaid) "Yes" else "No"
                tvLen.text = it.number.length.toString()
                tvLunh.text = if (it.number.luhn) "Yes" else "No"
                it.country?.let { country ->
                    tvAlpha.text = country.alphaTwo
                    tvCountyName.text = country.name
                    tvCoordinates.text = getString(
                        R.string.coordinates,
                        country.latitude.toString(),
                        country.longitude.toString()
                    )
                }
                it.bank?.let { bank ->
                    tvBankName.text = bank.name
                    tvBankUrl.text = bank.url
                }
            }
        }
    }

    private fun addTextChangeListener(){
        binding.etBin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.loadCardInfo(binding.etBin.text?.toString())
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}