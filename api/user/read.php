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
include_once "../config/auth.php";

//creating database connection and user object
$database = new Database();
$db = $database->getConnection();
$user = new User($db);

//creating auth
$auth = new Auth();

//getting jwt token
$jwt = null;
$data = json_decode(file_get_contents("php://input"));
$authHeader = $_SERVER['HTTP_AUTHORIZATION'];
$arr = explode(" ", $authHeader);
$jwt = $arr[1];

//verifying jwt Token
if($auth->verifyToken($jwt)){
    //calling user read method which querries database
    $stmt = $user->read();
    $num = $stmt->rowCount();

    //making sure records are not empty
    if($num>0){

        //creating an array of users
        $user_arr = array();
        $user_arr["records"] = array();


        while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);

            $user_item=array(
                "Username" => $Username,
                "Name" => $Name,
                "StartDate" => $StartDate,
                "EmployeeID" => $EmployeeID,
                "Rank" => $Rank
            );

            array_push($user_arr["records"],$user_item);
        }

        //OK
        http_response_code(200);

        // encoding user array in json
        echo json_encode($user_arr);


    }

    else{

        // Not found
        http_response_code(404);

        // tell the user no users found
        echo json_encode( array("message" => "No products found."));
    }
}
else{

    //Access Denied
    http_response_code(401);
    echo json_encode(array(
        "message" => "Access denied.",
    ));

}


?>
