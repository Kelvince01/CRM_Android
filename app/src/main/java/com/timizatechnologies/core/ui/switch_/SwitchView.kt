package com.timizatechnologies.core.ui.switch_

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.timizatechnologies.crm.R
//import kotlin.jvm.internal.k
//import kotlin.jvm.internal.s
//import o2.h;

open class SwitchView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    /*renamedfrom:switch*/
    private lateinit var f0switch: CompoundButton
    private lateinit var switchLabel: AppCompatTextView

    /*constructor(context: Context, attributeSet: AttributeSet, i: Int) : super() {
        s.f(context, "context")
        ViewGroup.inflate(context, R.layout.fs_switch, this)
        setBackground(h.f(getResources(), R.drawable.bg_list_selector, context.getTheme()))
        var findViewById: View = findViewById(R.id.switchLabel)
        s.e(findViewById, "findViewById(R.id.switchLabel)")
        switchLabel = findViewById as AppCompatTextView
        findViewById = findViewById(R.id.switchCompat)
        s.e(findViewById, "findViewById(R.id.switchCompat)")
        val compoundButton: CompoundButton = findViewById as CompoundButton
        f0switch = compoundButton
        compoundButton.setId(-1)
        setupAttributes(attributeSet)
    }

    private fun setupAttributes(attributeSet: AttributeSet) {
        val obtainStyledAttributes: TypedArray = getContext().getTheme().obtainStyledAttributes(
            attributeSet,
            R.styleable.IconifiedView, 0, 0
        )
        s.e(obtainStyledAttributes, "context.theme.obtainStyl…able.IconifiedView,0,0)")
        val drawable: Drawable =
            obtainStyledAttributes.getDrawable(R.styleable.IconifiedView_iconStart)
        obtainStyledAttributes.recycle()
        if (drawable != null) {
            val i: Int = R.id.switch_icon
            (findViewById(i) as ImageView).setImageDrawable(drawable)
            (findViewById(i) as ImageView).setVisibility(0)
        }
    }

    /*synthetic*/   constructor(attributeSet: AttributeSet?, i: Int, i2: Int, kVar: k?) {
        var attributeSet: AttributeSet? = attributeSet
        var i = i
        if (i2 and 2 != 0) {
            attributeSet = null
        }
        if (i2 and 4 != 0) {
            i = 0
        }
        this(context, attributeSet, i)
    }

    val switch: CompoundButton
        get() {
            return this.f0switch
        }

    fun getSwitchLabel(): AppCompatTextView {
        return this.switchLabel
    }

    fun setChecked() {
        f0switch.setChecked(z)
    }

    constructor() {
        s.f(context, "context")
        this(context, null, 0, 6, null)
    }

    constructor(context: Context?, attributeSet: AttributeSet?) {
        s.f(context, "context")
        this(context, attributeSet, 0, 4, null)
    }

    companion object {
        const val `$stable` = 8
    } */
}
