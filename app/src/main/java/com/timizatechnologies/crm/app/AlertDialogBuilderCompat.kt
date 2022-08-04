package com.timizatechnologies.crm.app

import android.annotation.SuppressLint
import android.app.AlertDialog.Builder
import android.content.Context
import android.view.ContextThemeWrapper
import com.timizatechnologies.crm.R

class AlertDialogBuilderCompat {
    @SuppressLint("ResourceType")
    fun create(context: Context) : Builder {
        return Builder(ContextThemeWrapper(context, 16973939))
    }
}
