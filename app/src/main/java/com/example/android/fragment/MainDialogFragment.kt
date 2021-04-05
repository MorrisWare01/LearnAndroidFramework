package com.example.android.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * @author mmw
 * @date 2020/5/22
 **/
class MainDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext()) {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setCancelable(true)
                setCanceledOnTouchOutside(true)
                window?.setGravity(Gravity.CENTER)
            }

            override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {
            }

            override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {

            }

            override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View(layoutInflater.context).apply {
            minimumWidth = 400
            minimumHeight = 400
            setBackgroundColor(Color.RED)
            setOnClickListener {
                dismiss()
            }
        }
    }

}