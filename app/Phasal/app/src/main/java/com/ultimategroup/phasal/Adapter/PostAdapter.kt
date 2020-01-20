package com.ultimategroup.phasal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ultimategroup.phasal.Model.Post
import com.ultimategroup.phasal.Model.User
import com.ultimategroup.phasal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class PostAdapter
    (private val mContext: Context,
     private val mPost:List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>()
{

    private var firebaseUser:FirebaseUser?=null
    private var liked :String = "no"



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_post_layout,parent,false)

        return ViewHolder(view)

    }



    override fun getItemCount(): Int {
        return mPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val post= mPost[position]
        Picasso.get().load(post.getPostimage()).into(holder.postimage)
        holder.description.text=post.getDescription()
        holder.comments.text=post.getComment()

        publisherInfo(holder.profileimage,holder.username,holder.publisher,post.getPublisher(),holder.description,holder.likes,post.getPostid(),holder.comments)

        //postInfo(holder.description,holder.likes,post.getPostid(),holder.comment)

        liked=iniLike(post.getPostid(),holder.likebtn,liked)

        holder.likebtn.setOnClickListener {
            if(liked == "no")
            {
                FirebaseDatabase.getInstance().reference
                        .child("Likes").child(post.getPostid())
                        .child(firebaseUser!!.uid).setValue(true)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                holder.likebtn.setImageResource(R.drawable.ic_thumb_up_alt)
                                liked ="yes"


                            }


                        }
                likecount(post.getPostid(), holder.likes, liked, holder.likebtn)
            }else if(liked == "yes"){

                FirebaseDatabase.getInstance().reference
                    .child("Likes").child(post.getPostid())
                    .child(firebaseUser!!.uid).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            holder.likebtn.setImageResource(R.drawable.ic_thumb_up_clicked)
                            liked="no"

                        }


                    }
                likecount(post.getPostid(), holder.likes, liked, holder.likebtn)

            }


        }
        likecount(post.getPostid(), holder.likes, liked, holder.likebtn)



    }

    private fun iniLike(
        postid: String,
        likebtn: ImageView,
        liked: String
    ): String {
        val likeiconRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Likes").child(postid).child(it1.toString())
        }
        var yes :String="yes"
        likeiconRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(liked=="no"){
                    if(p0.exists() ){
                        likebtn.setImageResource(R.drawable.ic_thumb_up_clicked)
                        this@PostAdapter.liked =yes
                    }
                    else {
                        likebtn.setImageResource(R.drawable.ic_thumb_up_alt)
                    }
                 }

            }
        })
        return liked

    }

    private fun likecount(
        postid: String,
        likes: TextView,
        liked: String,
        likebtn: ImageView
    ) {
        val likecountRef =
            FirebaseDatabase.getInstance().reference
                .child("Likes").child(postid)


        likecountRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    likes.text=p0.childrenCount.toString()
                }
                else{
                    likes.text="0"
                }

            }
        })

    }


    private fun publisherInfo(
        profileimage: CircleImageView,
        username: TextView,
        publisher: TextView,
        publisherID: String,
        description: TextView,
        likes: TextView,
        postid: String,
        comments: TextView
    ) {
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(publisherID)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){

                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.ic_profile).into(profileimage)
                    username.text = user!!.getUsername()
                    publisher.text = user!!.getfullname()

                }



            }
        })

    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var profileimage : CircleImageView
        var postimage : ImageView
        var likebtn: ImageView
        /*var commentbtn : ImageView
        var save_btn: ImageView
        */
        var username : TextView
        var publisher :TextView
        var likes:TextView
        var description : TextView
        var comments : TextView

        init {

            profileimage= itemView.findViewById(R.id.user_profile_image_search)
            postimage= itemView.findViewById(R.id.post_image_home)
            likebtn= itemView.findViewById(R.id.post_image_like_btn)
            /*commentbtn= itemView.findViewById(R.id.post_image_comment_btn)
            save_btn= itemView.findViewById(R.id.post_save_comment_btn)
            */
            username= itemView.findViewById(R.id.user_name_search)
            publisher= itemView.findViewById(R.id.publisher)
            likes= itemView.findViewById(R.id.likes)
            description= itemView.findViewById(R.id.description)
            comments= itemView.findViewById(R.id.comments)

        }
    }




}