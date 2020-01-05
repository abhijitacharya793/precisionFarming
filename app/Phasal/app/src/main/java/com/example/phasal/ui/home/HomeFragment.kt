package com.example.phasal.ui.home

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androdocs.httprequest.HttpRequest
import com.example.phasal.R
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var weatherbar: ImageView? = null
    var temperaturebar : TextView? = null
    var cropbar: TextView? = null

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Titlebar
        val titlebar: TextView = root.findViewById(R.id.home_titlebar)
        homeViewModel.title.observe(this, Observer {
            titlebar.text = it
        })

        // Weather and temperature
        try
        {
            callAPI().execute()
        }catch(ex:Exception){}

        weatherbar = root.findViewById(R.id.weather)
        weatherbar!!.setImageResource(R.drawable.ic_na)


        temperaturebar = root.findViewById(R.id.temperature)
        homeViewModel.temperature.observe(this, Observer {
            temperaturebar!!.text = it
        })

        // Place
        val placebar: TextView = root.findViewById(R.id.place)
        homeViewModel.place.observe(this, Observer {
            placebar.text = it
        })

        // Date
        val datebar: TextView = root.findViewById(R.id.date)
        homeViewModel.date.observe(this, Observer {
            datebar.text = it
        })

        //Crop List
//        cropbar = root.findViewById(R.id.home_crop)


        return root
    }

//    Weather   ////////////////////////////////////////////////////////////////////////////////////
    inner class callAPI : AsyncTask<String, Void?, String>()
    {
        // init variables
        var CITY = "airoli,in"
        var API = "7f18907e711fb3244d87cce034f9d8ca"
        var jsonWeather:String? = null
        var jsonCropCount:String? = null
        var jsonCroplist:String? = null

        override fun doInBackground(vararg params: String?): String {
            try
            {
                jsonWeather = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                jsonCroplist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/crops/?format=json")
                jsonCropCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/crop_count/?format=json")
                return " "
            }catch (ex:Exception)
            {
                return " "
            }
        }

        fun setWeather()
        {
            val jsonObj = JSONObject(jsonWeather)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

            val temp = main.getString("temp").toString()
//                val tempMin = "Min Temp: " + main.getString("temp_min").toString() + "°C"
//                val tempMax = "Max Temp: " + main.getString("temp_max").toString() + "°C"
//                val pressure = main.getString("pressure")
//                val humidity = main.getString("humidity")
//
//                val sunrise = sys.getLong("sunrise")
//                val sunset = sys.getLong("sunset")
//                val windSpeed = wind.getString("speed")
            val weatherDescription = weather.getString("description")

            val address = jsonObj.getString("name").toString() + ", " + sys.getString("country")

            temperaturebar?.text = temp.subSequence(0,2)

            when(weatherDescription)
            {
                "clear sky" -> weatherbar!!.setImageResource(R.drawable.ic_clear_day)
                "drizzle" -> weatherbar!!.setImageResource(R.drawable.ic_drizzle)
                "smoke" -> weatherbar!!.setImageResource(R.drawable.ic_smoke)
                "haze" -> weatherbar!!.setImageResource(R.drawable.ic_haze)
                "moderate rain" -> weatherbar!!.setImageResource(R.drawable.ic_showers)
                "overcast clouds" -> weatherbar!!.setImageResource(R.drawable.ic_cloudy)
                "scattered clouds" -> weatherbar!!.setImageResource(R.drawable.ic_cloudy)
                "few clouds" -> weatherbar!!.setImageResource(R.drawable.ic_partlycloudy_day)
            }
        }

        fun setCropList()
        {
            val count:Int = jsonCropCount?.subSequence(0,1).toString().toInt()

            var name = Array<String>(count){""}
            var desc = Array<String>(count){""}
            var crop:JSONObject? = null

            val jsonObj = JSONArray(jsonCroplist)

            for (i in 0..(count-1)!!)
            {
                crop = JSONObject(jsonObj[i].toString())
                name[i] = crop.getString("name")
                desc[i] = crop.getString("summary")
            }

//            cropbar?.text = name[1]
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try
            {
                setWeather()
                setCropList()


            }catch (ex:Exception){
                cropbar?.text = "Error"
            }
        }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////


}