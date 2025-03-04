package com.example.booksapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object Util {

    fun switchFragment(fragmentManager: FragmentManager,containerViewId:Int,targetFragment:Fragment,addToBackStack:Boolean){
        if(addToBackStack){
            fragmentManager.beginTransaction().replace(containerViewId,targetFragment).addToBackStack(null).commit()
        }
        else{
            fragmentManager.beginTransaction().replace(containerViewId,targetFragment).commit()

        }
    }
}