package com.bekircaglar.yellowapp.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bekircaglar.yellowapp.databinding.FragmentSingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class SingUpFragment : Fragment() {
    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage : FirebaseStorage



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        binding.emailTexup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                val inputText = p0.toString()
                var replacement = inputText.replace("@gmail.com","")
                binding.usernameTextup.setText(replacement)


            }


        })





        val myText = binding.logintextup.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(myText)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {
                val action4 = SingUpFragmentDirections.actionSingUpFragmentToLogInFragment()
                Navigation.findNavController(view).navigate(action4)
            }
        }
        val startIndex = myText.indexOf("Log in")
        val endIndex = startIndex + "Log in".length
        spannableStringBuilder.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.logintextup.apply {
            text = spannableStringBuilder
            movementMethod = LinkMovementMethod.getInstance() // Tıklanabilir metni etkinleştirir
            highlightColor = Color.rgb(33,149,232) // Tıklanan metni vurgulamamak için
        }


        binding.buttonsingup.setOnClickListener {
            var mail = binding.emailTexup.text.toString()
            var pass = binding.passwordTextup.text.toString()




            if (mail.equals("")  ||  pass.equals("")){
                Toast.makeText(requireContext(), "Enter Password And Email", Toast.LENGTH_LONG).show()



            }
            else{
                auth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener {

                   val act3 = SingUpFragmentDirections.actionSingUpFragmentToFeedFragment()
                    Navigation.findNavController(view).navigate(act3)


                }.addOnFailureListener {

                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}