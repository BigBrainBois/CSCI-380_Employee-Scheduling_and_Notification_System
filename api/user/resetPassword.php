<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
// include database and object files
include_once '../config/database.php';
include_once '../objects/user.php';
  
// get database connection
$database = new Database();
$db = $database->getConnection();
  
// prepare product object
$user= new User($db);
  
// get id of request to be edited
$data = json_decode(file_get_contents("php://input"));
  
// set ID property of request to be edited
$user->Username = $data->Username;
 
// set product property values

$user->Password = $data->Password;
$user->EmployeeID = $data->EmployeeID;
$user->Rank = $data->Rank; 



  
// update the product
if($user->resetPassword()){
  
    // set response code - 200 ok
    http_response_code(200);
  
    // tell the user
    echo json_encode(array("message" => "Password was reset."));
}
  
// if unable to update the product, tell the user
else{
  
    // set response code - 503 service unavailable
    http_response_code(503);
  
    // tell the user
    echo json_encode(array("message" => "Unable to reset password ."));
}
?>