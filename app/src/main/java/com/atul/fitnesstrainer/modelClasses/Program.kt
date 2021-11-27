package com.atul.fitnesstrainer.modelClasses

/**
 * All the Information about a particular training Program
 */
data class Program(
    var id: Int,
    var name: String,
    var category_id: Int,
    var trainer_id: Int,
    var image: String,
    var duration: Long,
    var min_calories: Long,
    var max_calories: Long,
    var description: String,
    var difficulty_level: Int,
    var createdAt: Long,
    var updatedAt: String,
    var video: String,
    var video_uploadedAt: String,
    var equipments: ArrayList<String>,
    var workout_plans: List<WorkoutPlans>,
    var trainer_name: String,
    var category_name: String,
    var difficulty_level_name: String
)
