package com.myogardener.kotlin_realtime_database_8.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.myogardener.kotlin_realtime_database_8.HomeFragmentDirections
import com.myogardener.kotlin_realtime_database_8.R
import com.myogardener.kotlin_realtime_database_8.User
import com.myogardener.kotlin_realtime_database_8.UserViewHolder
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var mAdapter: FirebaseRecyclerAdapter<User, UserViewHolder>? = null
    private var fuser: FirebaseUser? = null
    lateinit var ref: DatabaseReference

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fuser = FirebaseAuth.getInstance().currentUser
        ref = FirebaseDatabase.getInstance().getReference("users")

        val query = ref.child(fuser!!.uid).child("Data").limitToLast(10)

        val childEventListener: ChildEventListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(User::class.java)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(User::class.java)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(User::class.java)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val message = snapshot.getValue(User::class.java)
            }
        }

        query.addChildEventListener(childEventListener)

        val options = FirebaseRecyclerOptions.Builder<User>().setQuery(query, User::class.java).setLifecycleOwner(this).build()

        mAdapter = object : FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
return UserViewHolder(
    LayoutInflater.from(
        parent.context
    ).inflate(
        R.layout.item_user,
        parent,
        false
    )
)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                holder.bind(model)
                holder.itemView.setOnClickListener{
            onclick(model)
             }
            }
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        layoutManager.reverseLayout = false
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = mAdapter

        floatingActionButtonAdd.setOnClickListener {
            findNavController().popBackStack()
        }

        btn_logout.setOnClickListener {
logout()
        }
    }

    override fun onStart() {
        super.onStart()
        mAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter!!.stopListening()
    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        findNavController().popBackStack()
    }

    fun deleteData(key:String){

        ref!!.child(fuser!!.uid).child("Data").child(key).removeValue()
    }

    fun onclick(user: User) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Delete item")
            setMessage("Are you sure to delete?")
            setIcon(android.R.drawable.ic_dialog_alert)


            setPositiveButton("Yes") { dialogInterface, i ->
                deleteData(user.key.toString())
                Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show()
            }

            setNegativeButton("No") { dialogInterface, i ->
                Toast.makeText(context, "Delete cancel", Toast.LENGTH_SHORT).show()
            }

            setNeutralButton("Update") {dialogInterface,i->
                var action =
                    HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                        user.key.toString(),
                        user.name.toString(),
                        user.age.toString(),
                        user.position.toString()
                    )

findNavController().navigate(action)
//            dialogInterface, i ->
//                val updateBuilder = AlertDialog.Builder(context)
//                val dialogLayout = layoutInflater.inflate(
//                    R.layout.fragment_update, null
//                )
//                updateBuilder.apply {
//                    setTitle("Update Book")
//                    setView(dialogLayout)
//                    setPositiveButton("Ok") { dialogInterface, i ->
//                        //update လုပ်လိုက်တဲ့ Data တွေကို book_table က bookName ထဲကိုထည့်
//                        val updateText = dialogLayout.edtUpdateName.text.toString()
//                        bookViewModel.updateItem(updateText, book.bookName)
//                    }
//                }
//                val updateDialog: AlertDialog = updateBuilder.create()
//                updateDialog.show()
            }
        }.show()

    }
}