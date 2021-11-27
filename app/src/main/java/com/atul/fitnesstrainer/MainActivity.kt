package com.atul.fitnesstrainer

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.atul.fitnesstrainer.databinding.ActivityMainBinding
import com.atul.fitnesstrainer.modelClasses.Program
import com.atul.fitnesstrainer.modelClasses.ResponseData
import com.atul.fitnesstrainer.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var progerssProgressDialog: ProgressDialog
    lateinit var programList:ArrayList<Program>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        programList = ArrayList()
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getData()
        binding.showTestValue.setOnClickListener {
            binding.testView.text = programList[0].toString()
        }
    }

    private fun getData() {
        val call: Call<ResponseData> = ApiClient.getClient.getPrograms()
        call.enqueue(object:Callback<ResponseData>{
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                progerssProgressDialog.dismiss()
                if(response.isSuccessful) {
                    response.body()?.data?.let { programList.addAll(it) }
                    binding.testView.text = programList.toString()
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                progerssProgressDialog.dismiss()
                Log.i("Error",t.message.toString())
                Toast.makeText(this@MainActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
            }

        })
    }
}