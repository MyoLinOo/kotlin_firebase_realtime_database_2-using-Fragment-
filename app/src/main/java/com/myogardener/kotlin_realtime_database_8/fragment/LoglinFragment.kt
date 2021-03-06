package com.myogardener.kotlin_realtime_database_8.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.myogardener.kotlin_realtime_database_8.LoglinFragmentDirections
import com.myogardener.kotlin_realtime_database_8.R
import kotlinx.android.synthetic.main.fragment_loglin.*



class LoglinFragment : Fragment() {

//    var googleSignClient: GoogleSignInClient? = null
//    val RC_SIGN = 1000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loglin, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_create_account.setOnClickListener {
         createEmailId()

        }
        btn_login.setOnClickListener {
            loginEmail()

        }

    }
    fun createEmailId() {
        var email = edt_email.text.toString().trim()
        var password = edt_password.text.toString().trim()
    if(email.isEmpty() || password.isEmpty()){
    Toast.makeText(context,"Please input your email and password",Toast.LENGTH_LONG).show()
    }else{
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->

            if (task.isSuccessful) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(context,"sign success",Toast.LENGTH_LONG).show()
                moveNextPage()
            }else{
                Toast.makeText(context,"Something is wrong",Toast.LENGTH_LONG).show()
            }
        }
      }
    }
    fun loginEmail() {
        var email = edt_email.text.toString().trim()
        var password = edt_password.text.toString().trim()
if (email.isEmpty() || password.isEmpty()){
    Toast.makeText(context,"Please input email and password",Toast.LENGTH_LONG).show()
}
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(context,"Login success",Toast.LENGTH_LONG).show()
                    moveNextPage()
                }else{
                    Toast.makeText(context,"Something is wrong",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun moveNextPage() {
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            var action =
                LoglinFragmentDirections.actionLoglinFragmentToAddFragment()
            findNavController().navigate(action)

        }else{
            Toast.makeText(context,"current is null", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        moveNextPage()
    }
}