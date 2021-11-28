package com.atul.fitnesstrainer

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var adapter:GridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setToolBar()
        programList = ArrayList()
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getAllData()


    }

    private fun setToolBar() {
        setSupportActionBar(binding.toolbarBid)
        supportActionBar?.setTitle("Navigation")
        val toggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbarBid,R.string.open,R.string.close)
        toggle.syncState()
        binding.drawerLayout.addDrawerListener(toggle)
    }

    private fun setDataToAdapter() {
        if(isFetched){
            adapter = GridViewAdapter(this,programList)
            binding.gridView.numColumns = 2
//            binding.gridView.horizontalSpacing = 15
            binding.gridView.verticalSpacing = 15
            binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH
            binding.gridView.adapter = adapter
        }

    }

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

    private fun getAllData(){
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
