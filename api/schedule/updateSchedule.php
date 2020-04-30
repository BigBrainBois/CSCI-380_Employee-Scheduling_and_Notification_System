<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
// include database and object files
include_once '../config/database.php';
include_once '../objects/schedule.php';
  
// get database connection
$database = new Database();
$db = $database->getConnection();
  
// prepare product object
$schedule = new Schedule($db);
  
// get id of request to be edited
$data = json_decode(file_get_contents("php://input"));
  
// set ID property of request to be edited
$schedule->ID = $data->ID;
  
// set product property values
$schedule->EmployeeID = $data->EmployeeID;
$schedule->Date = $data->Date;
$schedule->StartTine = $data->StartTine;
$schedule->EndTine = $data->EndTine;

  
// update the product
if($schedule->updateSchedule()){
  
    // set response code - 200 ok
    http_response_code(200);
  
    // tell the user
    echo json_encode(array("message" => "Schedule form was updated."));
}
  
// if unable to update the product, tell the user
else{
  
    // set response code - 503 service unavailable
    http_response_code(503);
  
    // tell the user
    echo json_encode(array("message" => "Unable to update schedule."));
}
?>