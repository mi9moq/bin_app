package com.example.bankapp.presentation

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        binding.btLoadData.setOnClickListener {
            viewModel.loadCardInfo(binding.etBin.text?.toString())
        }
        binding.tilBin.setEndIconOnClickListener {
            binding.etBin.text?.clear()
            viewModel.loadCardInfo(null)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.cardInfo.collect {
                    setupViews(it)
                }
            }
        }
    }

    private fun setupViews(bin: BinInfo?){
        with(binding){
            if(bin == null){
                setupDefault(tvScheme)
                setupDefault(tvBrand)
                setupDefault(tvLen)
                setupDefault(tvLunh)
                setupDefault(tvType)
                setupDefault(tvPrepaid)
                setupDefault(tvAlpha)
                setupDefault(tvCountyName)
                setupDefault(tvCoordinates)
                setupDefault(tvBankName)
                setupDefault(tvBankUrl)
                setupDefault(tvBankPhone)
                setupDefault(tvBankCity)
            }else{
                bin.let {
                    tvScheme.text = it.scheme
                    tvType.text = it.type
                    tvBrand.text = it.brand
                    tvPrepaid.text = if (it.prepaid != null && it.prepaid) "Yes" else "No"
                    tvLen.text = it.number.length.toString()
                    tvLunh.text = if (it.number.luhn) "Yes" else "No"
                    if(it.country != null){
                        tvAlpha.text = it.country.alphaTwo
                        tvCountyName.text = it.country.name
                        tvCoordinates.text = getString(
                            R.string.coordinates,
                            it.country.latitude.toString(),
                            it.country.longitude.toString()
                        )
                    }
                    it.bank?.let { bank ->
                        tvBankName.text = bank.name
                        tvBankUrl.apply {
                            text = bank.url
                            linksClickable = true
                            autoLinkMask = Linkify.WEB_URLS
                        }
                        tvBankPhone.apply {
                            text = bank.phone
                            autoLinkMask = Linkify.PHONE_NUMBERS
                        }
                        tvBankCity.text = bank.city
                    }
                }
            }
        }
    }

    private fun setupDefault(textView: TextView){
        textView.text = QUESTION
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val QUESTION = "?"
    }
}