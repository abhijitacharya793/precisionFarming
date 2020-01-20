package com.ultimategroup.phasal.Fragments


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView

import com.androdocs.httprequest.HttpRequest
import com.ultimategroup.phasal.R
import com.google.android.material.snackbar.Snackbar
import com.ultimategroup.phasal.Model.*
import kotlinx.android.synthetic.main.fragment_crop_data.*
import kotlinx.android.synthetic.main.view_crop_data_crop_view.*
import kotlinx.android.synthetic.main.view_crop_data_crop_view.view.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class cropDataFragment : Fragment() {

    var weatherbar: ImageView? = null
    var temperaturebar : TextView? = null

    var listOfCrops = ArrayList<Crop>()
    var listOfDiseases = ArrayList<Disease>()
    var listOfSoilZones = ArrayList<SoilZone>()
    var listOfDistricts = ArrayList<District>()
    var listOfSeasons = ArrayList<Season>()

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

        try
        {
            callAPI().execute()
        }
        catch(ex:Exception){
            Snackbar.make(root!!, "ERROR!!!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        return root
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_data, container, false)
    }

    inner class CropAdapter : RecyclerView.Adapter<CustomViewHolder>{
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
            val cellForRow = layoutInflater.inflate(R.layout.view_crop_data_crop_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val crop= listOfCrops.get(position)
            val cropId = crop.id
            val cropName= crop.name
            val cropDes= crop.des
            val cropSeason= crop.season


            when(cropName){
                "rice" -> holder.view.home_crop_image!!.setImageResource(R.drawable.rice)
                "wheat" -> holder.view.home_crop_image!!.setImageResource(R.drawable.wheat)
                "groundnut" -> holder.view.home_crop_image!!.setImageResource(R.drawable.peanut)
                "sugarcane" -> holder.view.home_crop_image!!.setImageResource(R.drawable.sugarcane)
            }
            holder?.view?.home_crop_name.text= cropName
            var fragment: Fragment? = null
            holder.itemView.setOnClickListener {
                    v -> Toast.makeText(v.context, position.toString() + "", Toast.LENGTH_SHORT).show()
                when (v.getId()) {
                    R.id.home_crop ->  {
                        fragment = cropDetailedFragment()
//                        replaceFragment(fragment)
                        (v.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragment_container,cropDetailedFragment()).commit()
                    }
                }
            }
        }
    }

    inner class SoilZoneAdapter : RecyclerView.Adapter<CustomViewHolder>{
        var listOfSoilZone = ArrayList<SoilZone>()

        constructor(listOfSoilZone:ArrayList<SoilZone>):super(){
            this.listOfSoilZone = listOfSoilZone
        }

        //number of item
        override fun getItemCount(): Int {
            return listOfSoilZone.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            //create view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.view_crop_data_soil_zone_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val soil_zone= listOfSoilZone.get(position)
            val soil_zoneName = soil_zone.name
            holder?.view?.home_crop_name.text= soil_zoneName
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

    inner class DiseaseAdapter : RecyclerView.Adapter<CustomViewHolder>{
        var listOfDisease = ArrayList<Disease>()

        constructor(listOfDisease:ArrayList<Disease>):super(){
            this.listOfDisease = listOfDisease
        }

        //number of item
        override fun getItemCount(): Int {
            return listOfDisease.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            //create view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.view_crop_data_disease_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val disease= listOfDisease.get(position)
            val diseaseName = disease.name
            holder?.view?.home_crop_name.text= diseaseName
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

    inner class DistrictAdapter : RecyclerView.Adapter<CustomViewHolder>{
        var listOfDistrict = ArrayList<District>()

        constructor(listOfDistrict:ArrayList<District>):super(){
            this.listOfDistrict = listOfDistrict
        }

        //number of item
        override fun getItemCount(): Int {
            return listOfDistrict.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            //create view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.view_crop_data_district_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val district= listOfDistrict.get(position)
            val districtName = district.name
            holder?.view?.home_crop_name.text= districtName
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

    inner class SeasonAdapter : RecyclerView.Adapter<CustomViewHolder>{
        var listOfSeasons = ArrayList<Season>()

        constructor(listOfSeasons:ArrayList<Season>):super(){
            this.listOfSeasons = listOfSeasons
        }

        //number of item
        override fun getItemCount(): Int {
            return listOfSeasons.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            //create view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.view_crop_data_season_view, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val seasons= listOfSeasons.get(position)
            val seasonsName = seasons.name
            holder?.view?.home_crop_name.text= seasonsName
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
        var jsonDiseaseCount:String? = null
        var jsonDiseaselist:String? = null
        var jsonSoilZoneCount:String? = null
        var jsonSoilZonelist:String? = null
        var jsonDistrictCount:String? = null
        var jsonDistrictlist:String? = null
        var jsonSeasonCount:String? = null
        var jsonSeasonlist:String? = null

        override fun doInBackground(vararg params: String?): String {
            try
            {
                jsonWeather = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
            }catch (ex:Exception){}
            try
            {
                jsonCroplist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/crops/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonCropCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/crop_count/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonDiseaselist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/diseases/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonDiseaseCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/disease_count/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonSoilZonelist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/soil_zones/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonSoilZoneCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/soil_zone_count/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonDistrictlist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/districts/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonDistrictCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/district_count/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonSeasonlist = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/seasons/?format=json")
            }catch (ex:Exception){}
            try
            {
                jsonSeasonCount = HttpRequest.excuteGet("https://abhijitacharya.pythonanywhere.com/plantbase/api/season_count/?format=json")
            }catch (ex:Exception){}

            return " "
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

            var id = Array<String>(count){""}
            var name = Array<String>(count){""}
            var desc = Array<String>(count){""}
            var season = Array<Int>(count){0}
            var crop: JSONObject? = null

            val jsonObj = JSONArray(jsonCroplist)

            for (i in 0..(count-1)!!)
            {
                crop = JSONObject(jsonObj[i].toString())
                id[i] = crop.getString("id")
                name[i] = crop.getString("name")
                desc[i] = crop.getString("summary")
                season[i] = crop.getString("season").toInt()

                listOfCrops.add(Crop(id[i], name[i], desc[i], season[i]))
            }

            home_crop_list_hor.adapter = CropAdapter(listOfCrops)
        }

        fun setDiseaseList()
        {
            val count:Int = jsonDiseaseCount?.subSequence(0,1).toString().toInt()

            var id = Array<Int>(count){0}
            var name = Array<String>(count){""}
            var desc = Array<String>(count){""}
            var type = Array<String>(count){""}
            var diagnosis = Array<String>(count){""}
            var causes = Array<String>(count){""}
            var prevention = Array<String>(count){""}
            var treatment = Array<String>(count){""}
            var stage = Array<String>(count){""}
            var disease: JSONObject? = null

            val jsonObj = JSONArray(jsonDiseaselist)

            for (i in 0..(count-1)!!)
            {
                disease = JSONObject(jsonObj[i].toString())
                id[i] = disease.getString("id").toInt()
                name[i] = disease.getString("name")
                desc[i] = disease.getString("description")
                type[i] = disease.getString("name")
                diagnosis[i] = disease.getString("name")
                causes[i] = disease.getString("name")
                prevention[i] = disease.getString("name")
                treatment[i] = disease.getString("name")
                stage[i] = disease.getString("name")

                listOfDiseases.add(Disease(id[i], name[i], desc[i], type[i], diagnosis[i], causes[i], prevention[i], treatment[i], stage[i]))
            }

            home_disease_list_hor.adapter = DiseaseAdapter(listOfDiseases)
        }

        fun setDistrictList()
        {
            val count:Int = jsonDistrictCount?.subSequence(0,1).toString().toInt()

            var id = Array<Int>(count){0}
            var name = Array<String>(count){""}
            var soil_zone = Array<String>(count){""}
            var district: JSONObject? = null

            val jsonObj = JSONArray(jsonDistrictlist)

            for (i in 0..(count-1)!!)
            {
                district = JSONObject(jsonObj[i].toString())
                id[i] = district.getString("id").toInt()
                name[i] = district.getString("name")
                soil_zone[i] = district.getString(("soil_zone"))
                listOfDistricts.add(District(id[i], name[i], soil_zone[i]))
            }

            home_district_list_hor.adapter = DistrictAdapter(listOfDistricts)
        }

        fun setSoilZoneList()
        {
            val count:Int = jsonSoilZoneCount?.subSequence(0,1).toString().toInt()

            var id = Array<Int>(count){0}
            var name = Array<String>(count){""}
            var characteristics = Array<String>(count){""}
            var soil_zone: JSONObject? = null

            val jsonObj = JSONArray(jsonSoilZonelist)

            for (i in 0..(count-1)!!)
            {
                soil_zone = JSONObject(jsonObj[i].toString())
                id[i] = soil_zone.getString("id").toInt()
                name[i] = soil_zone.getString("name")
                characteristics[i] = soil_zone.getString("summary")
                listOfSoilZones.add(SoilZone(id[i], name[i], characteristics[i]))
            }

            home_soil_zone_list_hor.adapter = SoilZoneAdapter(listOfSoilZones)
        }

        fun setSeasonList()
        {
            val count:Int = jsonSeasonCount?.subSequence(0,1).toString().toInt()

            var id = Array<Int>(count){0}
            var name = Array<String>(count){""}
            var start_month = Array<String>(count){""}
            var end_month = Array<String>(count){""}
            var season: JSONObject? = null

            val jsonObj = JSONArray(jsonSeasonlist)

            for (i in 0..(count-1)!!)
            {
                season = JSONObject(jsonObj[i].toString())
                id[i] = season.getString("id").toInt()
                name[i] = season.getString("name")
                start_month[i] = season.getString("summary")
                end_month[i] = season.getString("summary")
                listOfSeasons.add(Season(id[i], name[i], start_month[i], end_month[i]))
            }

//            home_soil_zone_list_hor.adapter = SeasonAdapter(listOfSeasons)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try
            {
                setWeather()
            }catch (ex:Exception){}
            try
            {
                setCropList()
            }catch (ex:Exception){}
            try
            {
                setDiseaseList()
            }catch (ex:Exception){}
            try
            {
                setDistrictList()
            }catch (ex:Exception){}
            try
            {
                setSoilZoneList()
            }catch (ex:Exception){}
            try
            {
                setSeasonList()
            }catch (ex:Exception){}

        }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
