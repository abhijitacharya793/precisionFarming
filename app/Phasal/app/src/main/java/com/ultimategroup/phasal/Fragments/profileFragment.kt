package com.ultimategroup.phasal.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageSwitcher
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ultimategroup.phasal.AccountSettingsActivity
import com.ultimategroup.phasal.Adapter.GalleryAdapter
import com.ultimategroup.phasal.Model.Gallery
import com.ultimategroup.phasal.Model.User
import com.ultimategroup.phasal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class profileFragment : Fragment() {
    private lateinit var profileID :String
    private lateinit var firebaseUser:FirebaseUser
    private var galleryAdapter: GalleryAdapter? =null
    private var gallerylist :MutableList<Gallery>?=null





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if (pref!=null){
            this.profileID = pref.getString("profileId","none").toString()
        }
        if(profileID==firebaseUser.uid){
            view.btn_edit_profile.text = "Edit Profile"
        }
        else if (profileID != firebaseUser.uid){
            checkFollowandFollowing()
        }

        var post_view: LinearLayout = view.findViewById(R.id.post_layout)
        var detail_view: LinearLayout = view.findViewById(R.id.profile_username)
        var visible_btn:ImageButton = view.findViewById(R.id.visible)
        var invisible_btn:ImageButton = view.findViewById(R.id.invisible)

        post_view.visibility = View.GONE
        detail_view.visibility = View.VISIBLE

        invisible_btn.setOnClickListener{
            post_view.visibility = View.VISIBLE
            detail_view.visibility = View.GONE

        }
        visible_btn.setOnClickListener{
            post_view.visibility = View.GONE
            detail_view.visibility = View.VISIBLE

        }

        view.btn_edit_profile.setOnClickListener {
            val getBtnText = view.btn_edit_profile.text.toString()
            when{
                getBtnText == "Edit Profile" -> startActivity(Intent(context,AccountSettingsActivity::class.java))

                getBtnText =="Follow" -> {
                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(""+it1.toString())
                            .child("Following").child(profileID).setValue(true)
                    }
                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileID)
                            .child("Followers").child(""+it1.toString()).setValue(true)
                    }
                    getFollowers()

                }
                getBtnText =="Following" -> {
                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(""+it1.toString())
                            .child("Following").child(profileID).removeValue()
                    }
                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileID)
                            .child("Followers").child(""+it1.toString()).removeValue()
                    }
                    getFollowers()

                }

            }
        }



        var recyclerView: RecyclerView? = null
        recyclerView=view.findViewById(R.id.recycler_view_gallery)
        val linearLayoutManager= LinearLayoutManager(context)
        linearLayoutManager.reverseLayout=true
        linearLayoutManager.stackFromEnd=true
        recyclerView.layoutManager=linearLayoutManager
        gallerylist= ArrayList()
        galleryAdapter = context?.let { GalleryAdapter(it,gallerylist as ArrayList<Gallery>) }
        recyclerView.adapter = galleryAdapter



        getFollowers()
        getFollowing()
        getUserinfo()
        retriveMyPost(profileID)
        return view



    }





    private fun retriveMyPost(profileID: String) {
        val postRef =
            FirebaseDatabase.getInstance().reference
                .child("Post")
        postRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                gallerylist?.clear()

                for(snapshot in p0.children){
                    val gallery = snapshot.getValue(Gallery::class.java)

                    if(gallery!!.getPublisher() == profileID){
                        gallerylist!!.add(gallery)
                    }
                    galleryAdapter!!.notifyDataSetChanged()
                    view?.total_posts?.text = gallerylist?.size.toString()


                }
            }
        })
    }


    private fun checkFollowandFollowing() {
        val followRef =firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(""+it1.toString())
                .child("Following")
        }
        followRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.child(profileID).exists()){
                    view?.btn_edit_profile?.text="Following"
                }
                else{
                    view?.btn_edit_profile?.text="Follow"
                }
            }
        })
    }
    private fun getFollowers(){
        val followersRef =
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileID)
                .child("Followers")

        followersRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    view?.total_followers?.text=p0.childrenCount.toString()

                }

            }
        })

    }
    private fun getFollowing(){
        val followingRef =
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileID)
                .child("Following")

        followingRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    view?.total_following?.text=p0.childrenCount.toString()

                }

            }
        })

    }
    private fun getUserinfo(){
        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(profileID)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                /*if(context != null) {
                    return
                }*/
                if(p0.exists()){
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.ic_profile).into(view?.profile_image)
                    view?.profile_fragment_username?.text= user.getUsername()
                    view?.full_name_label?.text= user.getfullname()
                    view?.profile_bio?.text= user.getbio()
                }
            }

        })
    }


    override fun onStart() {
        super.onStart()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }
}

