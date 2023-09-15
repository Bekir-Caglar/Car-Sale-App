package com.bekircaglar.yellowapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bekircaglar.yellowapp.dataclass.Post
import com.bekircaglar.yellowapp.R
import com.bekircaglar.yellowapp.adapter.RecycleAdapter
import com.bekircaglar.yellowapp.databinding.FragmentFeedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postlist : ArrayList<Post>
    private lateinit var postadapter : RecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        postlist = ArrayList<Post>()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        postadapter = RecycleAdapter(requireContext(),postlist)
        binding.recyclerview.adapter = postadapter
        setHasOptionsMenu(true)




        getdata()
        return view


    }
         fun getdata(){

             db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                 if (error != null){
                     Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
                 }
                 else{
                     if (value != null){
                         if (!value.isEmpty){

                             val doc = value.documents
                             postlist.clear()

                             for (doc in doc){
                                 val model = doc.get("Model") as String
                                 val Email = doc.get("usermail") as String
                                 val downloadurl = doc.get("downloadurl") as String
                                 val title = doc.get("Title") as String
                                 val fuel = doc.get("Fuel") as String
                                 val km = doc.get("Km") as String
                                 val price = doc.get("Price") as String
                                 val newuser = Email.replace("@gmail.com","").toString().capitalize()
                                 val post = Post(newuser,title,model,km,fuel,price,downloadurl)
                                 postlist.add(post)


                             }
                             postadapter.notifyDataSetChanged()
                         }

                     }
                 }


             }



         }


         override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){


            inflater.inflate(R.menu.menu_selected, menu)
            return super.onCreateOptionsMenu(menu,inflater)
            println("2")
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {


            when (item.itemId) {
                R.id.addcarbutton -> {
                    val act4 = FeedFragmentDirections.actionFeedFragmentToUploadFragment()
                    Navigation.findNavController(binding.root).navigate(act4)
                    return true
                }
                R.id.logoutbutton -> {
                    auth.signOut()
                    val act5 = FeedFragmentDirections.actionFeedFragmentToLogInFragment()
                    Navigation.findNavController(binding.root).navigate(act5)
                    return true
                }


                else -> return super.onOptionsItemSelected(item)

            }
        }

}









