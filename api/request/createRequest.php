<?php
//headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//include database and user class
include_once "../config/database.php";
include_once "../objects/request.php";

//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$request = new Request($db);

//getting posted data
$data = json_decode(file_get_contents("php://input"));

//checking if input is empty
if(
   !empty($data->EmployeeID) &&
   !empty($data->DateRequested) &&
   !empty($data->Status) &&
   !empty($data->RequestType)&&
   !empty($data->Message)
){
    //setting user attributes
    $request->EmployeeID = $data->EmployeeID;
    $request->DateRequested = $data->DateRequested;
    $request->Status = $data->Status;
    $request->RequestType = $data->RequestType;
    $request->Message = $data->Message;

    if($request->createRequest()){
        http_response_code(200);
        echo json_encode(array("message"=> "Successfully submitted request to supervisor!"));
    }
    else{
        http_response_code(503);
        echo json_encode(array("message"=> "Unable to submit request."));
        
    }
    
}
else{
    http_response_code(400);
    echo json_encode(array("message"=> "Failed to submit request. The fields input were incomplete."));
}

?>