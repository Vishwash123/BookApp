package com.example.booksapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.booksapp.databinding.FragmentSignUpBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel:AuthViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.skipAnimation.collect{skip->
                if(!skip){
                    playEntryAnimation()
                }
                else{
                    binding.background.translationY = 0f
                    binding.grass.translationY = 0f
                    binding.boy.translationY = 0f
                    playFadeInAnimation(0)

                }
            }

        }
        //entry animation
       // playEntryAnimation()

        //animation that plays on pressing sign up
        binding.signupButton.setOnClickListener {
            playSignUpAnimation {
                binding.linearLayout.visibility = View.GONE
                binding.signupButton.visibility = View.GONE
                binding.otherpt.visibility = View.GONE
                binding.loadingLottie.playAnimation()
                FirebaseService.firebaseAuth.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.signupPasswordEdittext.text.toString()).addOnSuccessListener {
                    val intent = Intent(requireContext(),MainActivity2::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                }
            }
        }

        binding.switchToLoginText.setOnClickListener{
            playSwitchingAnimation{
                Util.switchFragment(parentFragmentManager,R.id.auth_fragment_container,LoginFragment(),true)
            }

        }


    }

    private fun playFadeInAnimation(delay:Long){
        val fadeIn1 = ObjectAnimator.ofFloat(binding.linearLayout,"alpha",0f,1f)
        val fadeIn2 = ObjectAnimator.ofFloat(binding.otherpt,"alpha",0f,1f)
        val fadeIn3 = ObjectAnimator.ofFloat(binding.signupButton,"alpha",0f,1f)

        val fadeinAnimatorSet = AnimatorSet()
        fadeinAnimatorSet.playTogether(fadeIn1,fadeIn2,fadeIn3)
        fadeinAnimatorSet.duration = 1500
        fadeinAnimatorSet.startDelay=delay
        fadeinAnimatorSet.interpolator = DecelerateInterpolator()
        fadeinAnimatorSet.start()
    }
    private fun playEntryAnimation(){



        ObjectAnimator.ofFloat(binding.background, "translationY", 0f).apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            start()

            binding.grass.postDelayed({
                ObjectAnimator.ofFloat(binding.grass, "translationY", 0f).apply {
                    duration = 1400
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }, 0)


            binding.boy.postDelayed({
                ObjectAnimator.ofFloat(binding.boy, "translationY", 0f).apply {
                    duration = 1400
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }, 100)


            playFadeInAnimation(1250)

        }
    }

    private fun playSwitchingAnimation(onAnimationCompleted:()->Unit){
        val fadeOut1 = ObjectAnimator.ofFloat(binding.linearLayout,"alpha",1f,0f)
        val fadeOut2 = ObjectAnimator.ofFloat(binding.signupButton,"alpha",1f,0f)
        val fadeOut3 = ObjectAnimator.ofFloat(binding.otherpt,"alpha",1f,0f)

        val fadeOutAnimatorSet = AnimatorSet()
        fadeOutAnimatorSet.playTogether(fadeOut1,fadeOut2,fadeOut3)
        fadeOutAnimatorSet.duration=300
        fadeOutAnimatorSet.interpolator = DecelerateInterpolator()
        fadeOutAnimatorSet.start()
        fadeOutAnimatorSet.doOnEnd {
            onAnimationCompleted()
        }

    }




    private fun playSignUpAnimation(onAnimationCompleted: () -> Unit){



        val anim1 = ObjectAnimator.ofFloat(binding.grass, "translationY", 1500f).apply {
            duration = 1400
            interpolator = DecelerateInterpolator()


        }
        val anim2 = ObjectAnimator.ofFloat(binding.boy, "translationY", 1500f).apply {
            duration = 1400
            interpolator = DecelerateInterpolator()


        }

        binding.background.pivotX = 0f
        binding.background.pivotY = binding.background.height.toFloat()

        val scaleX = ObjectAnimator.ofFloat(binding.background, "scaleX", 1f, 0.87f) // Zoom out X
        val scaleY = ObjectAnimator.ofFloat(binding.background, "scaleY", 1f, 0.87f) // Zoom out Y

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY,anim1,anim2)
        animatorSet.duration = 800
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.start()
        playSwitchingAnimation(onAnimationCompleted)

//        anim1.start()
//        anim2.start()

    }
}


