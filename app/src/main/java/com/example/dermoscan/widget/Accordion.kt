package com.example.dermoscan.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import com.example.dermoscan.R

class Accordion(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    // variables
    private var opened = false
    private var isClicked = false

    // UI elements
    private val icon: ImageView
    val titleTv: TextView
    val textTv: TextView
    val confidenceTv: TextView
    private val titleHolder: CardView
    private val textHolder: CardView
    private val confidenceHolder: CardView

    // title and text to be displayed in the accordion
    private var title = ""
        set(value) {
            field = value
            invalidate()
        }
    private var text = ""
        set(value) {
            field = value
            invalidate()
        }

    private var confidenceText = ""
        set(value) {
            field = value
            invalidate()
        }

    // colors for title and text
    private var titleColor = 0XFF000000.toInt()
        set(@ColorInt colorInt) {
            field = colorInt
            invalidate()
        }
    private var textColor = 0XFF111111.toInt()
        set(@ColorInt colorInt) {
            field = colorInt
            invalidate()
        }
    private var confidenceColor = 0XFF000000.toInt()
        set(@ColorInt colorInt) {
            field = colorInt
            invalidate()
        }

    // size of title and text
    private var titleSize = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var textSize = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var confidenceSize = 10f
        set(value) {
            field = value
            invalidate()
        }

    init {

        // get values from custom parameters passed in xml declaration of view
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Accordion, 0, 0)

        title = typedArray.getString(R.styleable.Accordion_title) ?: title
        text = typedArray.getString(R.styleable.Accordion_text) ?: text
        confidenceText = typedArray.getString(R.styleable.Accordion_confidenceText) ?: confidenceText

        titleSize = typedArray.getDimension(R.styleable.Accordion_titleSize, titleSize)
        textSize = typedArray.getDimension(R.styleable.Accordion_textSize, textSize)
        confidenceSize = typedArray.getDimension(R.styleable.Accordion_confidenceSize, confidenceSize)


        titleColor = typedArray.getColor(R.styleable.Accordion_titleColor, titleColor)
        textColor = typedArray.getColor(R.styleable.Accordion_textColor, textColor)
        confidenceColor = typedArray.getColor(R.styleable.Accordion_confidenceColor, confidenceColor)


        // recycle the values
        typedArray.recycle()

        // inflate the default accordion layout
        inflate(context, R.layout.accordion, this)

        //val card = findViewById<CardView>(R.id.accordion_card)
        titleTv = findViewById(R.id.title)
        textTv = findViewById(R.id.text)
        confidenceTv = findViewById(R.id.textConfidence)
        icon = findViewById(R.id.arrow)

        icon.layoutParams.height = titleSize.toInt()
        icon.layoutParams.width = titleSize.toInt()
        icon.requestLayout()

        // set the styles of the values as passed
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        textTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        confidenceTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, confidenceSize)


        titleTv.setTextColor(titleColor)
        textTv.setTextColor(textColor)
        confidenceTv.setTextColor(confidenceColor)

        titleTv.text = title
        textTv.text = text
        confidenceTv.text = confidenceText

        titleHolder = findViewById(R.id.titleHolder)
        textHolder = findViewById(R.id.textHolder)
        confidenceHolder = findViewById(R.id.textConfidenceHolder)

        textHolder.visibility = View.GONE
        confidenceHolder.visibility = View.GONE

        // animate the text when title is clicked
        titleHolder.setOnClickListener {
            handleAccordion()
        }

    }

    // open/close the accordion on title press
    private fun handleAccordion() {

        // don't do anything if it has already been clicked
        // -> prevents messing up with multiple clicks before
        // animation ends
        if (!isClicked) {

            textHolder.visibility = if (opened) View.GONE else View.VISIBLE
            confidenceHolder.visibility = if (opened) View.GONE else View.VISIBLE

            turn(icon)
            opened = !opened
            isClicked = false

            //TODO: open the Accordion with a nice animation

            // NOTE: this does not work perfectly yet!!
            /*// start work on the text of the accordion
            textHolder.apply {

                // if the text is already visible start animation from
                // 100% opacity to 0 %
                // if it is gone, make is visible for smoother transition
                alpha = if (opened) 1f else 0f
                if (visibility == View.GONE) visibility = View.VISIBLE

                // flip the down arrow
                turn(icon)

                // start animating
                animate()
                    .alpha(
                        // if it is already opened, closed it
                        // else open it
                        if (opened) 0f
                        else 1f
                    )
                    .setDuration(300) // 300 millisecond
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // turn the visibility as the condition requires
                            // and change the control variables
                            visibility = if (opened) View.GONE else View.VISIBLE
                            opened = !opened
                            isClicked = false
                        }
                    })
            }*/
        }
    }

    // flips any view upside down/downside up
    private fun turn(view: View) {
        if (!opened) view.animate().rotation(180f).start()
        else view.animate().rotation(0f).start()
    }

}