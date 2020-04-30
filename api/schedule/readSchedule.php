<?php
// required header
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
// include database and object files
include_once "../config/database.php";
include_once "../objects/schedule.php";
  
// instantiate database and category object
$database = new Database();
$db = $database->getConnection();
  
// initialize object
$schedule = new Schedule($db);
  
// query categorys
$stmt = $schedule->readSchedule();
$num = $stmt->rowCount();
  
// check if more than 0 record found
if($num>0){

    //creating an array of users
    $schedule_arr = array();
    $schedule_arr["records"] = array();


    while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);

        $schedule_item=array(
            "ID" => $ID,
            "EmployeeID" => $EmployeeID,
            "Date" => $Date,
            "StartTime" => $StartTime,
            "EndTime" => $EndTime
        );

        array_push($schedule_arr["records"],$schedule_item);
    }

    //OK
    http_response_code(200);

    // encoding user array in json
    echo json_encode($schedule_arr);


}
  
else{
  
    // set response code - 404 Not found
    http_response_code(404);
  
    // tell the user no categories found
    echo json_encode(
        array("message" => "No request found.")
    );
}
?>