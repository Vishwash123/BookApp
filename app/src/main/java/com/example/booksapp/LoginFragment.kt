package com.example.booksapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.fragment.app.activityViewModels
import com.example.booksapp.databinding.FragmentLoginBinding
import com.example.booksapp.databinding.FragmentSignUpBinding


class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private val authViewModel:AuthViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.disableSignUpEntryAnimation()

        playEntryAnimation()

        binding.loginButton.setOnClickListener{
            playLoginAnimation {
                binding.linearLayoutLogin.visibility = View.GONE
                binding.loginButton.visibility = View.GONE
                binding.otherptLogin.visibility = View.GONE
                FirebaseService.firebaseAuth.signInWithEmailAndPassword(binding.loginEmailEdittext.text.toString(),binding.loginPasswordEditext.text.toString()).addOnSuccessListener {
                    val intent = Intent(requireContext(),MainActivity2::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        binding.switchToSignupText.setOnClickListener{
            playSwitchingAnimation {
                parentFragmentManager.popBackStack()
            }
        }

    }

    private fun playEntryAnimation(){
        val fadeIn1 = ObjectAnimator.ofFloat(binding.linearLayoutLogin,"alpha",0f,1f)
        val fadeIn2 = ObjectAnimator.ofFloat(binding.otherptLogin,"alpha",0f,1f)
        val fadeIn3 = ObjectAnimator.ofFloat(binding.loginButton,"alpha",0f,1f)

        val fadeinAnimatorSet = AnimatorSet()
        fadeinAnimatorSet.playTogether(fadeIn1,fadeIn2,fadeIn3)
        fadeinAnimatorSet.duration = 1500
        fadeinAnimatorSet.interpolator = DecelerateInterpolator()
        fadeinAnimatorSet.start()
    }


    private fun playSwitchingAnimation(onAnimationCompleted:()->Unit){
        val fadeOut1 = ObjectAnimator.ofFloat(binding.linearLayoutLogin,"alpha",1f,0f)
        val fadeOut2 = ObjectAnimator.ofFloat(binding.loginButton,"alpha",1f,0f)
        val fadeOut3 = ObjectAnimator.ofFloat(binding.otherptLogin,"alpha",1f,0f)

        val fadeOutAnimatorSet = AnimatorSet()
        fadeOutAnimatorSet.playTogether(fadeOut1,fadeOut2,fadeOut3)
        fadeOutAnimatorSet.duration=300
        fadeOutAnimatorSet.interpolator = DecelerateInterpolator()
        fadeOutAnimatorSet.start()
        fadeOutAnimatorSet.doOnEnd {
            onAnimationCompleted()
        }

    }

    private fun playLoginAnimation(onAnimationCompleted: () -> Unit){
        val anim1 = ObjectAnimator.ofFloat(binding.grassLogin, "translationY", 1500f).apply {
            duration = 1400
            interpolator = DecelerateInterpolator()
        }
        val anim2 = ObjectAnimator.ofFloat(binding.boyLogin, "translationY", 1500f).apply {
            duration = 1400
            interpolator = DecelerateInterpolator()


        }

        binding.backgroundLogin.pivotX = 0f
        binding.backgroundLogin.pivotY = binding.backgroundLogin.height.toFloat()

        val scaleX = ObjectAnimator.ofFloat(binding.backgroundLogin, "scaleX", 1f, 0.87f) // Zoom out X
        val scaleY = ObjectAnimator.ofFloat(binding.backgroundLogin, "scaleY", 1f, 0.87f) // Zoom out Y

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 800
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.start()
        playSwitchingAnimation(onAnimationCompleted)
        anim1.start()
        anim2.start()


    }


}