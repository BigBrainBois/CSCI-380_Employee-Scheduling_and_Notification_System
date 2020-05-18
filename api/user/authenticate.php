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

//creating auth object
$auth = new Auth();

//getting posted data
$data = json_decode(file_get_contents("php://input"));

if(
    !empty($data->Username) &&
    !empty($data->Password) &&
    !empty($data->Rank)
){
    $user->Username = $data->Username;
    $user->Password = $data->Password;
    $user->Rank = $data->Rank;
    
    //authenticating
    if($user->authenticate()){

        http_response_code(200);

        //generating jwt token
        $jwt = $auth->generateToken($data->Username);
        $EmployeeID =$user->getEmployeeID();

        echo json_encode(array(
            "message"=> "Sign-in was successfull!",
            "jwt" => $jwt,
            "EmployeeID" => $EmployeeID
        ));
    }
    else{
        http_response_code(401);
        echo json_encode(array("message"=> "Sign-in failed"));
    }
}
else{
    http_response_code(400);
    echo json_encode(array("message"=> "Failed to sign in. The data input was incomplete."));
}


?>