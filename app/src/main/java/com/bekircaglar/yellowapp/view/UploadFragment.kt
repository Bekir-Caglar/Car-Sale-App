package com.bekircaglar.yellowapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bekircaglar.yellowapp.databinding.FragmentUploadBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class UploadFragment : Fragment() {
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var activityResultLanuncher : ActivityResultLauncher<Intent>
    var imagedata : Uri?= null
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage : FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage
        registerLauncher()




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.uploadbutton.setOnClickListener {

            val uuiid = UUID.randomUUID()
            val imagename = "$uuiid.jpg"
            var reference = storage.reference
            val imagesReferance = reference.child("images/$imagename")
            if (imagedata != null){

                imagesReferance.putFile(imagedata!!).addOnSuccessListener {
                    val uploadedReferance = storage.reference.child("images/$imagename")
                    uploadedReferance.downloadUrl.addOnSuccessListener {
                        val downloadurl = it.toString()
                        val postMap = hashMapOf<String,Any>()
                        postMap.put("downloadurl",downloadurl)
                        postMap.put("usermail",auth.currentUser!!.email!!)
                        postMap.put("Title",binding.TitleText.text.toString())
                        postMap.put("Fuel",binding.FuelType.text.toString())
                        postMap.put("Model",binding.ModelText.text.toString())
                        postMap.put("Km",binding.KilometerText.text.toString())
                        postMap.put("Price",binding.PriceText.text.toString())
                        postMap.put("date",com.google.firebase.Timestamp.now())


                        firestore.collection("Posts").add(postMap).addOnSuccessListener {
                            val act6 = UploadFragmentDirections.actionUploadFragmentToFeedFragment()
                            Navigation.findNavController(view).navigate(act6)

                        }.addOnFailureListener{
                            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                        }


                    }

                }


            }



        }





        binding.goGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 33){
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)){
                        Snackbar.make(view,"Need Permission", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                        }).show()



                    }
                    else {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                    }
                } else {
                    val intenttogallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)



                }


            }
            else{
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Snackbar.make(view,"Need Permission", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                        }).show()



                    }
                    else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                    }
                } else {
                    val intenttogallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)



                }
            }
        }





    return view


    }

    private fun registerLauncher(){
        activityResultLanuncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult(),){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                val intentfromresult = result.data
                if (intentfromresult != null){

                    imagedata = intentfromresult.data
                    imagedata?.let {
                        binding.goGallery.setImageURI(it)
                        
                    }

                }
            }

        }


        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
                result ->
            if (result){
                val intenttogallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLanuncher.launch(intenttogallery)


            }
            else{
                Toast.makeText(requireContext(),"Need Permission", Toast.LENGTH_LONG)
            }

        }



    }






}

