package com.atul.fitnesstrainer

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atul.fitnesstrainer.Filter.OnFiltersAdded
import com.atul.fitnesstrainer.adapters.GridViewAdapter
import com.atul.fitnesstrainer.databinding.ActivityMainBinding
import com.atul.fitnesstrainer.modelClasses.Program
import com.atul.fitnesstrainer.modelClasses.ResponseData
import com.atul.fitnesstrainer.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var isFetched: Boolean = false
    private lateinit var binding: ActivityMainBinding
    lateinit var progerssProgressDialog: ProgressDialog
    lateinit var programList: ArrayList<Program>
    lateinit var adapter: GridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        programList = ArrayList()

        // initialize progress bar
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        // fetch data
        getData()

        // filter the data
        binding.ivFilter.setOnClickListener {
            Filter().showDialog(this, binding, programList, object : OnFiltersAdded {
                override fun filters(
                    time: Float,
                    trainer: ArrayList<String>,
                    difficulty: ArrayList<String>
                ) {
                    filterList(time, trainer, difficulty)
                }

                override fun clearFilters() {
                    setDataToAdapter()
                }

            })
        }


    }

    /**
     * Apply filters to the list from selection
     */
    private fun filterList(time: Float, trainer: ArrayList<String>, difficulty: ArrayList<String>) {
        var newList: ArrayList<Program> = ArrayList()
        for (program in programList) {
            if (time > 2 && ((program.duration / 60L) >= time)) {
                newList.add(program)
            }
            if (trainer.size > 0) {
                for (train in trainer) {
                    if (program.trainer_name.contains(train)) {
                        if (!newList.contains(program)) {
                            newList.add(program)
                        }
                    }
                }
            }
            if (difficulty.size > 0) {
                for (diff in difficulty) {
                    if (program.difficulty_level_name.contains(diff)) {
                        if (!newList.contains(program)) {
                            newList.add(program)
                        }
                    }
                }
            }
        }
        adapter = GridViewAdapter(this, newList)
        binding.gridView.numColumns = 2
        binding.gridView.verticalSpacing = 15
        binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH
        binding.gridView.adapter = adapter
    }

    /**
     * Set Adapter for Grid View
     */
    private fun setDataToAdapter() {
        if (isFetched) {
            adapter = GridViewAdapter(this, programList)
            binding.gridView.numColumns = 2
            binding.gridView.verticalSpacing = 15
            binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH
            binding.gridView.adapter = adapter
        }

    }

    /**
     * To fetch the data withh category id =14
     */
    private fun getData() {
        val call: Call<ResponseData> = ApiClient.getClient.getPrograms()
        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                progerssProgressDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.data?.let { programList.addAll(it) }
                    isFetched = true
                    setDataToAdapter()
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                progerssProgressDialog.dismiss()
                Log.i("Error", t.message.toString())
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    /**
     * To fetch all the data without categoryID = 14
     */
    private fun getAllData() {
        val getCall: Call<ResponseData> = ApiClient.getClient.getAllData()
        getCall.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                progerssProgressDialog.dismiss()
                if (response.isSuccessful) {
                    response.body()?.data?.let { programList.addAll(it) }
                    isFetched = true
                    setDataToAdapter()
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                progerssProgressDialog.dismiss()
                Log.i("Error", t.message.toString())
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}
