package com.atul.fitnesstrainer

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import com.atul.fitnesstrainer.databinding.ActivityMainBinding
import com.atul.fitnesstrainer.databinding.ItemFiltersOptionBinding
import com.atul.fitnesstrainer.modelClasses.Program
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Filter {
    var equipment: ArrayList<String> = ArrayList()
    private var trainers: HashMap<String, Int> = HashMap()
    private var difficultyLevel: HashMap<String, Int> = HashMap()
    var v1: Boolean = false
    var v2: Boolean = false
    var v3: Boolean = false
    fun showDialog(context: Context?, binding: ActivityMainBinding, programList: ArrayList<Program>,
        listener: OnFiltersAdded) {
        // initialize binding for filters option
        val bind = ItemFiltersOptionBinding.inflate(
            LayoutInflater.from(context),
            binding.root,
            false
        )

        // separated out the data
        filterTrainersAndDiffLevel(programList)

        // added the view to the dialog
        MaterialAlertDialogBuilder(context!!)
            .setView(bind.root)
            .setPositiveButton("Apply Filters") { _, _ ->
                val time = bind.progressCircular.value
                val trainer = getSelectedTrainer(bind)
                val difficulty = getSelectedDifficulty(bind)
                listener.filters(time, trainer, difficulty)
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .setNeutralButton("Clear Filters") { _, _ ->
                listener.clearFilters()
            }
            .show()

        // initialize checkboxes for trainers
        setTrainers(context, bind)

        // initialize checkboxes with difficulty level
        setDifficultyLevel(context, bind)

        // Handling the views
        bind.cardView1.setOnClickListener {
            if (!v1) {
                bind.view1.visibility = View.VISIBLE
                bind.progressCircular.visibility = View.VISIBLE
                v1 = true
            } else {
                bind.view1.visibility = View.GONE
                bind.progressCircular.visibility = View.GONE
                v1 = false
            }
        }

        bind.cardView2.setOnClickListener {
            if (!v2) {
                bind.view2.visibility = View.VISIBLE
                bind.trainerCB.visibility = View.VISIBLE
                v2 = true
            } else {
                bind.view2.visibility = View.GONE
                bind.trainerCB.visibility = View.GONE
                v2 = false
            }
        }

        bind.cardView3.setOnClickListener {
            if (!v3) {
                bind.view3.visibility = View.VISIBLE
                bind.diffCB.visibility = View.VISIBLE
                v3 = true
            } else {
                bind.view3.visibility = View.GONE
                bind.diffCB.visibility = View.GONE
                v3 = false
            }
        }
    }

    /**
     * select all the selected difficulty on save
     */
    private fun getSelectedDifficulty(bind: ItemFiltersOptionBinding): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()
        val ll = bind.diffCB
        val childCount: Int = ll.childCount
        for (i in 0 until childCount) {
            val v = ll.getChildAt(i) as CheckBox
            if (v.isChecked) {
                result.add(v.text.toString())
            }
        }
        return result
    }

    /**
     * select all the selected trainers on save
     */
    private fun getSelectedTrainer(bind: ItemFiltersOptionBinding): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()
        val ll = bind.trainerCB
        val childCount: Int = ll.childCount
        for (i in 0 until childCount) {
            val v = ll.getChildAt(i) as CheckBox
            if (v.isChecked) {
                result.add(v.text.toString())
            }
        }
        return result
    }

    /**
     * Dynamically adding checkboxes
     */
    private fun setDifficultyLevel(context: Context, bind: ItemFiltersOptionBinding) {
        for (str in difficultyLevel) {
            val checkBox = CheckBox(context)
//            checkBox.layoutDirection = View.LAYOUT_DIRECTION_RTL
            checkBox.text = str.key
            checkBox.id = str.value
            checkBox.setTextColor(context.getColor(R.color.white))
            checkBox.backgroundTintList =
                ColorStateList.valueOf(context.resources.getColor(R.color.white))
            checkBox.setPadding(4, 4, 4, 4)
            bind.diffCB.addView(checkBox)
        }
    }

    /**
     * Dynamically adding checkboxes
     */
    private fun setTrainers(context: Context, bind: ItemFiltersOptionBinding) {
        for (str in trainers) {
            val checkBox = CheckBox(context)
//            checkBox.layoutDirection = View.LAYOUT_DIRECTION_RTL
            checkBox.text = str.key
            checkBox.id = str.value
            checkBox.setTextColor(context.getColor(R.color.white))
            checkBox.backgroundTintList =
                ColorStateList.valueOf(context.resources.getColor(R.color.white))
            checkBox.setPadding(4, 4, 4, 4)
            bind.trainerCB.addView(checkBox)
        }
    }

    /**
     * Filtering the data
     */
    private fun filterTrainersAndDiffLevel(programList: ArrayList<Program>) {
        for (program in programList) {
            if (!trainers.contains(program.trainer_name)) {
                trainers[program.trainer_name] = program.trainer_id
            }
            if (!difficultyLevel.contains(program.difficulty_level_name)) {
                difficultyLevel[program.difficulty_level_name] = program.difficulty_level
            }
        }
    }

    /**
     * Interface to send data to mainActivity
     */
    interface OnFiltersAdded {
        fun filters(time: Float, trainer: ArrayList<String>, difficulty: ArrayList<String>)
        fun clearFilters()
    }

}