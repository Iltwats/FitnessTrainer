package com.atul.fitnesstrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.rangeTo
import com.atul.fitnesstrainer.R
import com.atul.fitnesstrainer.modelClasses.Program

class GridViewAdapter(var context:Context,var programs:List<Program>): BaseAdapter() {

    override fun getCount(): Int {
        return programs.size
    }

    override fun getItem(p0: Int): Any {
        return programs[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view == null) {
            //inflate layout resource if its null
            view = LayoutInflater.from(context).inflate(R.layout.item_image_layout, p2, false)
        }

        //get current program
        val program = this.getItem(p0) as Program

        //reference textViews and imageViews from our layout
        val img = view!!.findViewById(R.id.imageView) as ImageView
        val workOutTxt = view.findViewById(R.id.workoutName) as TextView
        val trainerTxt = view.findViewById(R.id.trainer) as TextView
        val difficultyTxt = view.findViewById(R.id.difficulty) as TextView
        val durationTxt = view.findViewById(R.id.duration) as TextView

        //Bind data to TextView and ImageView
        workOutTxt.text = program.name.uppercase()
        trainerTxt.text = "With "+program.trainer_name
        difficultyTxt.text = program.difficulty_level_name
        durationTxt.text = ((program.duration/60L).toString())+"min"

        // Setting Dummy image
        val rand = (1..6).random()
        if(((program.image.substring(0,rand)).toInt())%2 == 0){
            img.setImageDrawable(context.getDrawable(R.drawable.imag))
        }else{
            img.setImageDrawable(context.getDrawable(R.drawable.imag1))
        }
        //handle itemClicks for the GridView
        view.setOnClickListener {
            Toast.makeText(context, program.description, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}