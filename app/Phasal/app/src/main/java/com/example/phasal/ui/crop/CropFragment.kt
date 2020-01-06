package com.example.phasal.ui.profile

import android.os.AsyncTask
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
import com.androdocs.httprequest.HttpRequest
import com.example.phasal.Crop
import com.example.phasal.R
import com.example.phasal.ui.forum.ForumFragment
import com.example.phasal.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class CropFragment : Fragment() {

    private lateinit var cropViewModel: CropViewModel
    var crop_name:TextView? = null
    var crop_des:TextView? = null
    var _crop:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cropViewModel = ViewModelProviders.of(this).get(CropViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_crop, container, false)

        val btn8: ImageView = root.findViewById(R.id.crop_closeButton) as ImageView
        crop_name = root.findViewById(R.id.home_crop_name)
        crop_des = root.findViewById(R.id.home_crop_des)

        var fragment: Fragment? = null
        val clickListener = View.OnClickListener {view ->

            when (view.getId()) {
                R.id.crop_closeButton ->  {
                    fragment = HomeFragment()
                    replaceFragment(fragment)
                }
            }
        }
        btn8.setOnClickListener(clickListener)

        try
        {
            callAPI().execute()
        }catch (ex:Exception){}

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

    inner class callAPI : AsyncTask<String, Void?, String>()
    {
        // init variables
        var crop = "groundnut"

        var jsonCropdata:String? = null

        override fun doInBackground(vararg params: String?): String {
            try
            {
                jsonCropdata = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/crop/?format=json&name=$crop")
                return " "
            }catch (ex:Exception)
            {
                return " "
            }
        }

        fun setCropData()
        {
            var name:String? = null
            var desc:String? = null
            var crop: JSONObject? = null

            crop = JSONObject(jsonCropdata)
            name = crop.getString("name")
            desc = crop.getString("summary")
            crop_name!!.text = name
            crop_des!!.text = desc

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try
            {
                setCropData()


            }catch (ex:Exception){}
        }

    }
}