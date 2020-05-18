
<?php
// required header
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
// include database and object files
include_once "../config/database.php";
include_once "../objects/request.php";
  
// instantiate database and category object
$database = new Database();
$db = $database->getConnection();
  
// initialize object
$request = new Request($db);
  
// query categorys
$stmt = $request->readRequest();
$num = $stmt->rowCount();
  
// check if more than 0 record found
if($num>0){

    //creating an array of users
    $request_arr = array();
    $request_arr["records"] = array();


    while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);

        $request_item=array(
            "RequestID" => $RequestID,
            "EmployeeID" => $EmployeeID,
            "DateRequested" => $DateRequested,
            "Status" => $Status,
            "RequestType" => $RequestType,
            "Message" => $Message
        );

        array_push($request_arr["records"],$request_item);
    }

    //OK
    http_response_code(200);

    // encoding user array in json
    echo json_encode($request_arr);


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