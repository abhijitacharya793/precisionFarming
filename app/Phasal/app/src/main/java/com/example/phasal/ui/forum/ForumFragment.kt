package com.example.phasal.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.phasal.R
import com.example.phasal.ui.home.HomeFragment
import com.example.phasal.ui.profile.SearchFragment


public class ForumFragment : Fragment() {

    private lateinit var forumViewModel: ForumViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forumViewModel = ViewModelProviders.of(this).get(ForumViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_forum, container, false)

        val searchbtn: ImageView = root.findViewById(R.id.forum_searchButton) as ImageView

        var answerView: TextView = root.findViewById(R.id.forum_answer)


        var fragment: Fragment? = null
        val clickListener = View.OnClickListener {view ->

            when (view.getId()) {
                R.id.forum_searchButton ->  {
                    fragment = SearchFragment()
                    replaceFragment(fragment)
                }
            }
        }
        searchbtn.setOnClickListener(clickListener)


        return root
    }

    fun replaceFragment(someFragment: Fragment?)
    {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        if (someFragment != null) {
            transaction.replace(R.id.nav_host_fragment, someFragment)
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }
}