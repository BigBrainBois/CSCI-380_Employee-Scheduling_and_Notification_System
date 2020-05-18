<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
// include database and object files
//include_once '../config/core.php';
include_once '../config/database.php';
include_once '../objects/employee.php';
  
// instantiate database and employee object
$database = new Database();
$db = $database->getConnection();
  
// initialize object
$employee = new Employee($db);
  
// get keywords
$SchedDate=isset($_GET["date"]) ? $_GET["date"] : "";
  
// query employees
$stmt = $employee->checkAvailability($SchedDate);
$num = $stmt->rowCount();
  
// check if more than 0 record found
if($num>0){
  
    // employee array
    $employee_arr=array();
    $employee_arr["available"]=array();
  
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);
  
        $employee_item=array(
            "EmployeeID" => $EmployeeID,
            "SickDaysUsed" => $SickDaysUsed,
            "SickDaysRemaining" => $SickDaysRemaining,
            "VacationDaysUsed" => $VacationDaysUsed,
            "VacationDaysRemaining" => $VacationDaysRemaining
        );
  
        array_push($employee_arr["available"], $employee_item);
    }
  
    // set response code - 200 OK
    http_response_code(200);
  
    // show products data
    echo json_encode($employee_arr);
}
  
else{
    // set response code - 404 Not found
    http_response_code(404);
  
    // tell the user no available employees
    echo json_encode(
        array("message" => "No employees are available on that day.")
    );
}
?>