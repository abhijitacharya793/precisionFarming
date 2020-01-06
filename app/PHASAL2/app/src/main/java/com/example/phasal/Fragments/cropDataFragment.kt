package com.example.phasal.Fragments


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.phasal.Model.Crop

import com.androdocs.httprequest.HttpRequest
import com.example.phasal.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_crop_data.*
import kotlinx.android.synthetic.main.home_crop_view.view.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class cropDataFragment : Fragment() {

    var weatherbar: ImageView? = null
    var temperaturebar : TextView? = null
    var listOfCrops = ArrayList<Crop>()
    var root:View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_crop_data, container, false)

        // Weather and temperature

        weatherbar = root!!.findViewById(R.id.weather)
        weatherbar!!.setImageResource(R.drawable.ic_na)


        temperaturebar = root!!.findViewById(R.id.temperature)

        // Place
        val placebar: TextView = root!!.findViewById(R.id.place)

        // Date
        val datebar: TextView = root!!.findViewById(R.id.date)

        val fab:View = root!!.findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            Snackbar.make(root!!, "Camera Activity", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        try
        {
            callAPI().execute()
        }
        catch(ex:Exception){}

        return root
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_data, container, false)
    }

    inner class MainAdapter : RecyclerView.Adapter<CustomViewHolder>{
        var listOfCrops = ArrayList<Crop>()

        constructor(listOfCrops:ArrayList<Crop>):super(){
            this.listOfCrops = listOfCrops
        }

        //number of item
        override fun getItemCount(): Int {
            return listOfCrops.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            //create view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.home_crop_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val crop= listOfCrops.get(position)
            val cropName = crop.name
            holder?.view?.home_crop_name.text= cropName
            var fragment: Fragment? = null
            holder.itemView.setOnClickListener {
                    v -> Toast.makeText(v.context, position.toString() + "", Toast.LENGTH_SHORT).show()
                when (v.getId()) {
                    R.id.home_crop ->  {
//                        fragment = CropFragment()
//                        replaceFragment(fragment)
                    }
                }
            }
        }
    }

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val crop_name= view.home_crop_name
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
            var crop: JSONObject? = null

            val jsonObj = JSONArray(jsonCroplist)

            for (i in 0..(count-1)!!)
            {
                crop = JSONObject(jsonObj[i].toString())
                name[i] = crop.getString("name")
                desc[i] = crop.getString("summary")
                listOfCrops.add(Crop(name[i], desc[i]))
            }

            home_crop_list_hor.adapter = MainAdapter(listOfCrops)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try
            {
                setWeather()
                setCropList()


            }catch (ex:Exception){}
        }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
