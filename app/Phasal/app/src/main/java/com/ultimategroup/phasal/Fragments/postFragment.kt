package com.ultimategroup.phasal.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ultimategroup.phasal.Adapter.PostAdapter
import com.ultimategroup.phasal.AddPostActivity
import com.ultimategroup.phasal.ForumActivity
import com.ultimategroup.phasal.Model.Post
import com.ultimategroup.phasal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_post.view.*

/**
 * A simple [Fragment] subclass.
 */
class postFragment : Fragment() {

    private var postAdapter: PostAdapter? =null
    private var postList :MutableList<Post>?=null
    private var followingsList : MutableList<Post>?=null
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view =  inflater.inflate(R.layout.fragment_post, container, false)

        view.camera_add_post.setOnClickListener {
            startActivity(Intent(context,AddPostActivity::class.java))
        }

        view.chat_home.setOnClickListener {
            startActivity(Intent(context, ForumActivity::class.java))

        }

            // Inflate the layout for this fragment
        var recyclerView:RecyclerView? = null
        recyclerView=view.findViewById(R.id.recycler_view_home)
        val linearLayoutManager=LinearLayoutManager(context)
        linearLayoutManager.reverseLayout=true
        linearLayoutManager.stackFromEnd=true
        recyclerView.layoutManager=linearLayoutManager
        postList= ArrayList()
        postAdapter = context?.let { PostAdapter(it,postList as ArrayList<Post>) }
        recyclerView.adapter = postAdapter

        checkFollowings()





        return view
    }

    private fun checkFollowings() {

        followingsList = ArrayList()

        val followingRef =
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Following")

        followingRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    (followingsList as ArrayList<String>).clear()
                    for(snapshot in p0.children){
                        snapshot.key?.let { (followingsList as ArrayList<String>).add(it)  }
                    }
                    retrivePost()
                }

            }
        })
    }

    private fun retrivePost() {
        val postRef =
            FirebaseDatabase.getInstance().reference
                .child("Post")
        postRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
            postList?.clear()

                for(snapshot in p0.children){
                    val post = snapshot.getValue(Post::class.java)
                    for (id in (followingsList as ArrayList<String>)){
                        if(post!!.getPublisher() == id){
                            postList!!.add(post)
                        }
                        postAdapter!!.notifyDataSetChanged()
                    }

                }
            }
        })

    }



}
