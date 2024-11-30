package com.example.money_manager_app.fragment.add.view

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.AddTransfer
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.FragmentAddTranferBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.money_manager_app.viewmodel.MainViewModel
import com.example.moneymanager.ui.add.adapter.AddTransferInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddTranferFragment : Fragment(), AddTransferInterface {

    private var _binding: FragmentAddTranferBinding? = null
    private var btnFee = false
    private val viewModel: AddViewModel by activityViewModels()
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            viewModel.setBitmap(it)
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val bitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, it))
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                }
                viewModel.setBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("PickImageLauncher", "Error decoding bitmap: ${e.message}")
                Toast.makeText(requireContext(), "Unable to load image.", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Log.e("PickImageLauncher", "No image selected")
            Toast.makeText(requireContext(), "No image selected.", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTranferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateDateTime()
        pickDate()
        pickTime()
        selectImage()
        observe()
        selectFromWallet()
        selectToWallet()
        setButtonFee()
    }

    fun setButtonFee() {
        binding.ivFee.setOnClickListener {

        }
    }

    fun selectFromWallet(){
        binding.fromWallet.setOnClickListener{
            setData()
            var bundle = bundleOf(
                "typeExpense" to 2,
                "isCheckWallet" to true,
            )
            findNavController().navigate(R.id.selectWalletFragment,bundle)
        }
    }

    fun selectToWallet(){
        binding.toWallet.setOnClickListener{
            setData()
            var bundle = bundleOf(
                "typeExpense" to 2,
                "isCheckWallet" to false,
            )
            findNavController().navigate(R.id.selectWalletFragment,bundle)
        }
    }

    fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedTime.collect { time ->
                    binding.etTime.setText(time)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedDate.collect { date ->
                    binding.etDate.setText(date)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentDateTime.collect { dateTime ->
                    binding.etDate.setText(dateTime.first)
                    binding.etTime.setText(dateTime.second)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageUri.collect { bitmap ->
                    binding.ivImage.setImageBitmap(bitmap)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amount.collect { amount ->
                    if(amount != 0.0){
                        binding.etAmount.setText(amount.toString())
                    } else {
                        binding.etAmount.setText("")
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.descriptor.collect { descriptor ->
                    binding.etDescription.setText(descriptor)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.momo.collect { momo ->
                    binding.etMemo.setText(momo)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fromWallet.collect { wallet ->
                    if (wallet.isNotEmpty()) {
                        binding.fromWallet.setText(wallet.first().name)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toWallet.collect { wallet ->
                    if (wallet.isNotEmpty()) {
                        binding.toWallet.setText(wallet.first().name)
                    }
                }
            }
        }
    }


    fun setData(){
        var amount = binding.etAmount.text.toString()
        var description = binding.etDescription.text.toString()
        var momo = binding.etMemo.text.toString()
        if (amount.isEmpty()) {
            amount = "0"
        }
        viewModel.setAmount(amount.toDouble())
        viewModel.setDescriptor(description)
        viewModel.setMomo(momo)
    }

    fun selectImage(){
        binding.ivMemo.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setItems(R.array.select_camera_and_gallery) { _, which ->
                when (which) {
                    0 -> {
                        setData()
                        if (requireActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            takePictureLauncher.launch(null)
                        } else {
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    }
                    1 -> {
                        setData()
                        pickImageLauncher.launch("image/*")
                    }
                }
            }
            builder.show()
        }
    }

    fun pickTime(){
        binding.etTime.setOnClickListener {
            viewModel.showTimePickerDialog(requireContext())
        }

    }


    fun pickDate(){
        binding.etDate.setOnClickListener {
            viewModel.showDatePickerDialog(requireContext())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        btnFee = false
    }

    override fun onSaveIncome() {
        TODO("Not yet implemented")
    }

    override fun onSaveExpense() {
        TODO("Not yet implemented")
    }

    override fun onSaveTransfer() {
        val amountText = binding.etAmount.text.toString()
        if (amountText.isNotEmpty()) {
            val amount = amountText.toDouble()
            val description = binding.etDescription.text.toString()
            val time = binding.etTime.text.toString()
            val date = binding.etDate.text.toString()
            val getbitmap = viewModel.getBitmap()
            var linkimg = viewModel.saveDrawableToAppStorage(requireContext(), getbitmap)
            if(linkimg == null){
                linkimg = ""
            }
            val typeOfExpenditure: TransferType = TransferType.Transfer
            val toWallet = viewModel.toWallet.value?.first()?.id ?: 0
            val fromWallet = viewModel.fromWallet.value?.first()?.id ?: 0
            val fee : Double = 0.0
            val accountId = mainViewModel.currentAccount.value?.account?.id ?: 0
            val name = binding.etMemo.text.toString()
            var iconId : Int = 1
            val typeDebt = ""
            val typeIconWallet = ""
            val colorId = 0
            val transfer = AddTransfer(
                fromWallet,
                toWallet,
                amount,
                name,
                fee,
                description,
                accountId,
                linkimg,
                date,
                time,
                typeOfExpenditure,
                typeDebt,
                iconId,
                colorId,
                typeIconWallet
            )
            viewModel.saveIncomeAndExpense(transfer)
            viewModel.onCleared()
            findNavController().navigate(R.id.mainFragment)
        } else {
            Log.e("AddExpenseFragment", "Amount is empty")
        }
    }

}