<?php

//headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//include database and user class
include_once "../config/database.php";
include_once "../objects/user.php";

//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$user = new User($db);



// //calling user read method which querries database
 $stmt = $user->read();
 $num = $stmt->rowCount();

// //making sure records are not empty
 if($num>0){

     //creating an array of users
     $user_arr = array();
     $user_arr["records"] = array();


     while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
         extract($row);

         $user_item=array(
             "Username" => $Username,
             "Name" => $Name,
             "Email" => $Email,
             "StartDate" => $StartDate,
             "EmployeeID" => $EmployeeID
         );

         array_push($user_arr["records"],$user_item);
     }

     // set response code - 200 OK
     http_response_code(200);

     // encoding user array in json
     echo json_encode($user_arr);


 }

 else{

     // set response code - 404 Not found
     http_response_code(404);

     // tell the user no users found
     echo json_encode( array("message" => "No products found."));
 }



?>
