package com.bekircaglar.yellowapp.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bekircaglar.yellowapp.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val currentuser = auth.currentUser

        if (currentuser != null){
            val act1 = LogInFragmentDirections.actionLogInFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(act1)
        }


        val myText = binding.uptext.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(myText)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {

                val action = LogInFragmentDirections.actionLogInFragmentToSingUpFragment()
                Navigation.findNavController(view).navigate(action)
            }

            }
        val startIndex = myText.indexOf("Sign up")
        val endIndex = startIndex + "Sign up".length
        spannableStringBuilder.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.uptext.apply {
            text = spannableStringBuilder
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.rgb(33,149,232)
        }

        binding.button.setOnClickListener {

            val mail = binding.emailText.text.toString()
            val pass = binding.passwordText.text.toString()

            if (mail.equals("") || pass.equals("")){
                val context = requireContext()
                val message = "Enter Email And Password"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, message, duration).show()
            }
            else {
                auth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener {

                    val act2 = LogInFragmentDirections.actionLogInFragmentToFeedFragment()
                    Navigation.findNavController(view).navigate(act2)


                }.addOnFailureListener {

                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG)
                }

            }



        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}