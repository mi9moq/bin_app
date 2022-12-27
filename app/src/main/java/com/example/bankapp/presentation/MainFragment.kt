package com.example.bankapp.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentMainBinding
import com.example.bankapp.domain.entity.CardInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var bins: Array<String> = emptyArray()

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as BinApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
            viewModel.loadCardInfo(binding.acBin.text.toString())
        }
        addTextChangeListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.cardInfo.collect {
                    setupViews(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorInputBin.collect {
                    val message = if (it) {
                        getString(R.string.error_input_bin)
                    } else {
                        null
                    }
                    binding.tilBin.error = message
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.binSet.collect {
                    bins = it.toTypedArray()
                    val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, bins)
                    binding.acBin.setAdapter(arrayAdapter)
                }
            }
        }
    }

    private fun setupViews(cardInfo: CardInfo?) {
        with(binding) {
            if (cardInfo == null) {
                setupDefaultViews()
            } else {
                cardInfo.let {
                    tvScheme.text = it.scheme
                    tvType.text = it.type
                    tvBrand.text = it.brand
                    tvPrepaid.text = if (it.prepaid != null && it.prepaid) "Yes" else "No"
                    tvLen.text = it.number.length.toString()
                    tvLunh.text = if (it.number.luhn) "Yes" else "No"
                    setupCountry(it)
                    setupBank(it)
                }
            }
        }
    }

    private fun addTextChangeListener() {
        binding.acBin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInput()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setupCountry(cardInfo: CardInfo) {
        with(binding) {
            if (cardInfo.country != null) {
                cardInfo.country.let {
                    tvAlpha.text = it.alphaTwo
                    tvCountyName.text = it.name
                    tvCountyNumeric.text = it.numeric
                    tvCoordinates.text = getString(
                        R.string.county_coordinates,
                        it.latitude.toString(),
                        it.longitude.toString()
                    )
                    tvCurrency.text = getString(
                        R.string.currency,
                        it.currency
                    )
                    parseCoordinates(cardInfo)
                }
            } else {
                setupDefault(tvAlpha)
                setupDefault(tvCountyName)
                setupDefault(tvCountyNumeric)
                tvCoordinates.text = getString(
                    R.string.county_coordinates,
                    DEFAULT_VALUE,
                    DEFAULT_VALUE
                )
                tvCurrency.text = getString(
                    R.string.currency,
                    DEFAULT_VALUE
                )
            }
        }
    }

    private fun setupBank(cardInfo: CardInfo) {
        with(binding) {
            if (cardInfo.bank != null) {
                cardInfo.bank.let {
                    tvBankName.text = it.name
                    tvBankUrl.text = it.url
                    tvBankPhone.text = it.phone ?: DEFAULT_VALUE
                    tvBankCity.text = it.city ?: DEFAULT_VALUE
                }
            } else {
                setupDefault(tvBankName)
                setupDefault(tvBankUrl)
                setupDefault(tvBankPhone)
                setupDefault(tvBankCity)
            }
        }
    }

    private fun parseCoordinates(cardInfo: CardInfo){
        binding.tvCoordinates.setOnClickListener {
            val coordinates = "geo:${cardInfo.country?.latitude},${cardInfo.country?.longitude}"
            val gmmIntentUri = Uri.parse(coordinates)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun setupDefaultViews() {
        with(binding) {
            setupDefault(tvScheme)
            setupDefault(tvBrand)
            setupDefault(tvLen)
            setupDefault(tvLunh)
            setupDefault(tvType)
            setupDefault(tvPrepaid)
            setupDefault(tvAlpha)
            setupDefault(tvCountyName)
            setupDefault(tvCountyNumeric)
            setupDefault(tvBankName)
            setupDefault(tvBankUrl)
            setupDefault(tvBankPhone)
            setupDefault(tvBankCity)
            tvCurrency.text = getString(
                R.string.currency,
                DEFAULT_VALUE
            )
            tvCoordinates.text = getString(
                R.string.county_coordinates,
                DEFAULT_VALUE,
                DEFAULT_VALUE
            )
        }
    }

    private fun setupDefault(textView: TextView) {
        textView.text = DEFAULT_VALUE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_VALUE = "?"
    }
}