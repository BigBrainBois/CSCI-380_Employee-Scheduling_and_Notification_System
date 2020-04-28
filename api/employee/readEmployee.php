
<?php
// required header
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
// include database and object files
include_once "../config/database.php";
include_once "../objects/employee.php";
  
// instantiate database and category object
$database = new Database();
$db = $database->getConnection();
  
// initialize object
$employee = new Employee($db);
  
// query categorys
$stmt = $employee->readEmployee();
$num = $stmt->rowCount();
  
// check if more than 0 record found
if($num>0){

    //creating an array of users
    $employee_arr = array();
    $employee_arr["records"] = array();


    while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);

        $employee_item=array(
            "EmployeeID" => $EmployeeID,
            "SickDaysUsed" => $SickDaysUsed,
            "SickDaysRemaining" => $SickDaysRemaining,
            "VacationDaysUsed" => $VacationDaysUsed,
            "VacationDaysRemaining" => $VacationDaysRemaining
        );

        array_push($employee_arr["records"],$employee_item);
    }

    //OK
    http_response_code(200);

    // encoding user array in json
    echo json_encode($employee_arr);


}
  
else{
  
    // set response code - 404 Not found
    http_response_code(404);
  
    // tell the user no categories found
    echo json_encode(
        array("message" => "No employees found.")
    );
}
?>