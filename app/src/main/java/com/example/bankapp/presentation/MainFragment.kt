package com.example.bankapp.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentMainBinding
import com.example.bankapp.domain.entity.BinInfo
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
        ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy{
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
            viewModel.loadCardInfo(binding.etBin.text.toString())
        }
        binding.tilBin.setEndIconOnClickListener {
            binding.etBin.text?.clear()
            viewModel.loadCardInfo(null)
            //setupDefaultViews()
        }
        addTextChangeListener()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.cardInfo.collect {
                    setupViews(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.binList.collect{
                    bins = it
                    val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,bins)
                    binding.AutoCompleteTextView.setAdapter(arrayAdapter)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.errorInputBin.collect{
                    val message = if(it){
                        getString(R.string.error_input_bin)
                    }else{
                        null
                    }
                    binding.tilBin.error = message
                }
            }
        }
    }

    private fun setupViews(bin: BinInfo?){
        with(binding){
            if(bin == null){
                setupDefaultViews()
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

    private fun addTextChangeListener(){
        binding.etBin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInput()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun setupDefaultViews(){
        with(binding){
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
        }
    }

    private fun setupDefault(textView: TextView){
        textView.text = DEFAULT_VALUE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val DEFAULT_VALUE = "?"
    }
}