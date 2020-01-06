package com.example.phasal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.phasal.Model.Gallery
import com.example.phasal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class GalleryAdapter
    (private val mContext:Context,
     private val mGallery:List<Gallery>):RecyclerView.Adapter<GalleryAdapter.ViewHolder>()
{
    private var firebaseUser: FirebaseUser?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.gallery_layout,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mGallery.size
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ViewHolder, position: Int) {

        firebaseUser = FirebaseAuth.getInstance().currentUser


        val gallery= mGallery[position]

        Picasso.get().load(gallery.getPostimage()).into(holder.gallerypost)
        holder.description.text=gallery.getDescription()
        holder.comments.text = gallery.getComment()

        getPostinfo(holder.description,holder.likeCount,gallery.getPostid(),firebaseUser!!.uid)

    }

    private fun getPostinfo(
        description: TextView,
        likeCount: TextView,
        postid: String,
        uid: String
    ) {
        /*val desRef = FirebaseDatabase.getInstance().reference
            .child("Post").child(postid)
        desRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.child("publisher").equals(uid)){
                    val gallery = p0.getValue<Gallery>(Gallery::class.java)
                    description.text= gallery?.getDescription()

                }

            }
        })*/

        val likeRef = FirebaseDatabase.getInstance().reference
            .child("Likes").child(postid)
        likeRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    likeCount.text=p0.childrenCount.toString()
                }
                else{
                    likeCount.text="0"
                }

            }
        })


    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){

        var gallerypost :ImageView
        var likeCount :TextView
        var description:TextView
        var comments :TextView


        init {
            gallerypost=itemView.findViewById(R.id.my_post)
            likeCount=itemView.findViewById(R.id.gallery_likes_count)
            description=itemView.findViewById(R.id.gallery_description)
            comments = itemView.findViewById(R.id.gallery_comments)

        }



    }


}