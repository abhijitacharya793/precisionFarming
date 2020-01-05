package com.example.phasal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.phasal.R
import com.example.phasal.ui.forum.ForumFragment

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val btn8: ImageView = root.findViewById(R.id.forum_closeButton) as ImageView

        var fragment: Fragment? = null
        val clickListener = View.OnClickListener {view ->

            when (view.getId()) {
                R.id.forum_closeButton ->  {
                    fragment = ForumFragment()
                    replaceFragment(fragment)
                }
            }
        }
        btn8.setOnClickListener(clickListener)
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