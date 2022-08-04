package com.timizatechnologies.core.ui.text

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.timizatechnologies.crm.R
//import kotlin.jvm.internal.k
//import kotlin.jvm.internal.s


open class ArrowTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    lateinit var label: TextView

    /*private fun setupAttributes(attributeSet: AttributeSet) {
        val obtainStyledAttributes =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.IconifiedView, 0, 0)
        jdk.internal.org.jline.utils.Colors.s.e(
            obtainStyledAttributes,
            "context.theme.obtainStyl…able.IconifiedView, 0, 0)"
        )
        val drawable = obtainStyledAttributes.getDrawable(R.styleable.IconifiedView_iconStart)
        obtainStyledAttributes.recycle()
        if (drawable != null) {
            val i = R.id.icon
            (findViewById<View>(i) as ImageView).setImageDrawable(drawable)
            (findViewById<View>(i) as ImageView).visibility = VISIBLE
        }
    }

    constructor(context: Context?, attributeSet: AttributeSet, i: Int) {
        jdk.internal.org.jline.utils.Colors.s.f(context, "context")
        inflate(context, R.layout.arrow_row, this)
        val findViewById = findViewById<View>(R.id.label)
        jdk.internal.org.jline.utils.Colors.s.e(findViewById, "findViewById(R.id.label)")
        label = findViewById as TextView
        setupAttributes(attributeSet)
    }

    / synthetic /

    constructor(
        context: Context?,
        attributeSet: AttributeSet?,
        i: Int,
        i2: Int,
        kVar: k?
    ) {
        var attributeSet = attributeSet
        var i = i
        if (i2 and 2 != 0) {
            attributeSet = null
        }
        if (i2 and 4 != 0) {
            i = 0
        }
        this(context, attributeSet, i)
    }

    constructor(context: Context?) {
        jdk.internal.org.jline.utils.Colors.s.f(context, "context")
        this(context, null, 0, 6, null)
    }

    constructor(context: Context?, attributeSet: AttributeSet?) {
        jdk.internal.org.jline.utils.Colors.s.f(context, "context")
        this(context, attributeSet, 0, 4, null)
    }

    companion object {
        const val `$stable` = 8
    } */
}

