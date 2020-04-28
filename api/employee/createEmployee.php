<?php
//headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//include database and user class
include_once "../config/database.php";
include_once "../objects/employee.php";

//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$user = new Employee($db);

//getting posted data
$data = json_decode(file_get_contents("php://input"));

//checking if input is empty
if(
   !empty($data->EmployeeID) &&
   !empty($data->SickDaysUsed) &&
   !empty($data->SickdaysRemaining) &&
   !empty($data->VacationDaysUsed) &&
   !empty($data->VacationDaysRemaining)
){
    //setting user attributes
    $user->EmployeeID = $data->EmployeeID;
    $user->SickDaysUsed = $data->SickDaysUsed;
    $user->SickdaysRemaining = $data->SickdaysRemaining;
    $user->VacationDaysUsed = $data->VacationDaysUsed;
    $user->VacationDaysRemaining = $data->VacationDaysRemaining;

    if($user->createEmployee()){
        http_response_code(200);
        echo json_encode(array("message"=> "Successfully added employee to table!"));
    }
    else{
        http_response_code(503);
        echo json_encode(array("message"=> "Unable to create a new employee."));
        
    }
    
}
else{
    http_response_code(400);
    echo json_encode(array("message"=> "Failed to create a new user. The data input was incomplete."));
}

?>