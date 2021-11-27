package com.atul.fitnesstrainer.modelClasses


data class Program(
    var id:Int,
    var name:String,
    var category_id:Int,
    var trainer_id:Int,
    var image:String,
    var duration:Long,
    var min_calories:Long,
    var max_calories:Long,
    var description:String,
    var difficulty_level:Int,
    var createdAt:Long,
    var updatedAt:String,
    var video:String,
    var video_uploadedAt:String,
    var equipments:ArrayList<String>,
    var workout_plans: List<WorkoutPlans>,
    var trainer_name:String,
    var category_name:String,
    var difficulty_level_name:String
)




/*
"id": 17,
"name": "Cardio",
"category_id": 14,
"trainer_id": 9,
"image": "1634042874336.png",
"duration": 2400,
"min_calories": 420,
"max_calories": 460,
"description": "This is a class for cardio",
"difficulty_level": 2,
"createdAt": 1632466990,
"updatedAt": null,
"video": "1634042874323.mp4",
"video_uploadedAt": null,
"equipments": [],
"workout_plans": [
{
"name": "Cardio",
"reps": "25"
}
],
"trainer_name": "Ankit",
"category_name": "Cardio",
"difficulty_level_name": "Intermediate"
 */
