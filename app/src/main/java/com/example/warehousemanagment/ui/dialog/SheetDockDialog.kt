package com.example.warehousemanagment.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetDockDialog(
    private val context: Context,
    private val onCallBackListener: OnClickListener
) : BottomSheetDialogFragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding= DialogSheetDestinyLocationBinding.inflate(inflater,container,false)

        binding.serialsCount.visibility = View.GONE
        binding.title.text = getString(R.string.choose_your_dock)

        onCallBackListener.init(
            binding.rv,binding.progressBar,binding.searchEdi
        )

        binding.closeImg.setOnClickListener {
            onCallBackListener.onCloseClick()
        }
        binding.clearImg.setOnClickListener {
            binding.searchEdi.setText("")
        }





        return binding.root
    }


    interface OnClickListener
    {
        fun onCloseClick()
        fun init(
            rv: RecyclerView, progressBar: ProgressBar, searchEdi: EditText
        )
    }
}