package com.bekircaglar.yellowapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bekircaglar.yellowapp.R
import com.bekircaglar.yellowapp.databinding.FragmentDetailsBinding
import com.bekircaglar.yellowapp.databinding.FragmentFeedBinding
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
private lateinit var binding:FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle:DetailsFragmentArgs by navArgs()

        val model = bundle.model
        val title = bundle.title
        val fuel = bundle.fuel
        val km = bundle.km
        val price = bundle.price
        val image = bundle.image


        Picasso.get().load(image).into(binding.imageView2)
        binding.textView.text =  title
        binding.textView3.text = "Model : $model"
        binding.textView4.text = "Fuel Type : $fuel"
        binding.textView5.text = "Km : $km"
        binding.textView6.text = "Price : $price"
        }








    }

