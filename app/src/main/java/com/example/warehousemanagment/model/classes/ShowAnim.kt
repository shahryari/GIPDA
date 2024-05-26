package com.example.warehousemanagment.model.classes

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ShowAnim(var view: View, var targetHeight: Int, var expand: Boolean) : Animation()
{
    override fun applyTransformation(time: Float, t: Transformation)
    {
        if (expand==true){
            view.layoutParams.height =((targetHeight * (time * time)).toInt())
        }else {view.layoutParams.height =((targetHeight * (1-time * time)).toInt())}


        view.requestLayout()
    }

    override fun initialize(
        width: Int, height: Int, parentWidth: Int,
        parentHeight: Int
    ) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }

    override fun hasEnded(): Boolean {
        return super.hasEnded()
    }
}