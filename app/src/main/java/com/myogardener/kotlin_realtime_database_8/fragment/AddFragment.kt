package com.myogardener.kotlin_realtime_database_8.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.myogardener.kotlin_realtime_database_8.R
import com.myogardener.kotlin_realtime_database_8.User
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {
   private var fuser: FirebaseUser? = null
    lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)





        fuser = FirebaseAuth.getInstance().currentUser
        ref = FirebaseDatabase.getInstance().getReference("users")

        btn_save.setOnClickListener {
            saveData()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }}

    fun saveData(){

        var name = edt_name.text.toString().trim()
        var age = edt_age.text.toString().trim()
        var position= edt_position.text.toString().trim()

        if (age.isEmpty()||name.isEmpty()|| position.isEmpty()) {
            Toast.makeText(context,"Please Enter your Data", Toast.LENGTH_SHORT).show()
        }else{
            var key = ref!!.child(fuser!!.uid).child("Data").push().key.toString()
            var user = User(

                key,
                name,
                age,
                position
            )

            ref.child(fuser!!.uid).child("Data").child(key).setValue(user)
            edt_age.setText("")
            edt_name.setText("")
            edt_position.setText("")
        }
    }

}