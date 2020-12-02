package com.myogardener.kotlin_realtime_database_8.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.myogardener.kotlin_realtime_database_8.R
import com.myogardener.kotlin_realtime_database_8.User


import kotlinx.android.synthetic.main.fragment_update.*


class UpdateFragment : Fragment() {

    private var fuser: FirebaseUser? = null
    lateinit var ref: DatabaseReference

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fuser = FirebaseAuth.getInstance().currentUser
        ref = FirebaseDatabase.getInstance().getReference("users")

edt_name_update.setText(args.name)
        edt_age_update.setText(args.age)
        edt_position_update.setText(args.position)
btn_Delete.setOnClickListener {
updateData(args.id)
}
    }
    fun updateData(key:String){

        var name = edt_name_update.text.toString()
        var age = edt_age_update.text.toString()
        var position = edt_position_update.text.toString()

        if(age.isEmpty()){
            Toast.makeText(context,"Please Enter your age", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"update successfully", Toast.LENGTH_LONG).show()
var user = User(args.id,name,age,position)
            ref!!.child(fuser!!.uid).child("Data").child(key).setValue(user)
            findNavController().popBackStack()
        }

    }
}