<?php
//headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//include database and user class
include_once "../config/database.php";
include_once "../objects/user.php";

//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$user = new User($db);

//getting posted data
$data = json_decode(file_get_contents("php://input"));

//checking if input is empty
if(!empty($data->Username) &&
   !empty($data->Name) &&
   !empty($data->Email) &&
   !empty($data->Password) &&
   !empty($data->EmployeeID))
){
    //setting user attributes
    $user->Username = $data->Username;
    $user->Name = $data->Name;
    $user->Email = $data->Email;
    $user->Password = $data->Password
    $user->EmployeeID = $data->EmployeeID;
    $user->Date = date("Y-m-d");

    if($user->create()){
        
    }
    
}

?>