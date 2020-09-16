<?php
//headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//include database and user class
include_once "../config/database.php";
include_once "../objects/schedule.php";


//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$schedule = new Schedule($db);

$SchedDate=isset($_GET["date"]) ? $_GET["date"] : "";
//getting posted data
$data = json_decode(file_get_contents("php://input"));

//checking if input is empty
if(
   !empty($data->StartTime) &&
   !empty($data->EndTime) 
){
    //setting user attributes
    $schedule->EmployeeID = $data->EmployeeID;
    $schedule->Date = $data->Date;
    $schedule->StartTime = $data->StartTime;
    $schedule->EndTime = $data->EndTime;

    if($schedule->generateSchedule($SchedDate)){
        http_response_code(200);
        echo json_encode(array("message"=> "Successfully added employee to schedule table!"));
    }
    else{
        http_response_code(503);
        echo json_encode(array("message"=> "Unable to create a new schedule entry."));
        
    }
    
}
else{
    http_response_code(400);
    echo json_encode(array("message"=> "Failed to create a new schedule entry The data input was incomplete."));
}

?>