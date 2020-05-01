<?php
class Employee{
    private $conn;
    private $table_name = "EmployeeInfo";

    public $EmployeeID;
    public $SickDaysUsed;
    public $SickDaysRemaining;
    public $VacationDaysUsed;
    public $VacationDaysRemaining;
    //public $SchedDate = "2020-05-01";


    public function __construct($db){
        $this->conn = $db;
    }

    function readEmployee(){

        //creating querry
        $query = "SELECT EmployeeID, SickDaysUsed, SickDaysRemaining, 
        VacationDaysUsed, VacationDaysRemaining
        FROM " . $this->table_name . "";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //executing querry
        $stmt->execute();
        return $stmt;

    }


    function createEmployee(){
        //creating insert querry
        $query = "INSERT INTO " . $this->table_name . "
        SET EmployeeID =:EmployeeID, SickDaysUsed =:SickDaysUsed, SickDaysRemaining=:SickDaysRemaining, 
        VacationDaysUsed=:VacationDaysUsed, VacationDaysRemaining=:VacationDaysRemaining ";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //cleaning data
        $this->EmployeeID = htmlspecialchars(strip_tags($this->EmployeeID));
        $this->SickDaysUsed = htmlspecialchars(strip_tags($this->SickDaysUsed));
        $this->SickDaysRemaining = htmlspecialchars(strip_tags($this->SickDaysRemaining));
        $this->VacationDaysUsed = htmlspecialchars(strip_tags($this->VacationDaysUsed));
        $this->VacationDaysRemaining = htmlspecialchars(strip_tags($this->VacationDaysRemaining));

        //binding data to querry
        $stmt->bindParam(":EmployeeID",$this->EmployeeID);
        $stmt->bindParam(":SickDaysUsed",$this->SickDaysUsed);
        $stmt->bindParam(":SickDaysRemaining",$this->SickDaysRemaining);
        $stmt->bindParam(":VacationDaysUsed",$this->VacationDaysUsed);
        $stmt->bindParam(":VacationDaysRemaining",$this->VacationDaysRemaining);

        if($stmt->execute()){
            return true;
        }
        
        return false;

    }
    // update the employeeinfo
    function updateEmployee(){
  
        // update query
        $query = "UPDATE
                    " . $this->table_name . "
                SET
                    SickDaysUsed =:SickDaysUsed,
                    SickDaysRemaining =:SickDaysRemaining,
                    VacationDaysUsed =:VacationDaysUsed,
                    VacationDaysRemaining =:VacationDaysRemaining
                WHERE
                    EmployeeID =:EmployeeID";
      
        // prepare query statement
        $stmt = $this->conn->prepare($query);
      
        // sanitize
        $this->SickDaysUsed=htmlspecialchars(strip_tags($this->SickDaysUsed));
        $this->SickDaysRemaining=htmlspecialchars(strip_tags($this->SickDaysRemaining));
        $this->VacationDaysUsed=htmlspecialchars(strip_tags($this->VacationDaysUsed));
        $this->VacationDaysRemaining=htmlspecialchars(strip_tags($this->VacationDaysRemaining));
        $this->EmployeeID=htmlspecialchars(strip_tags($this->EmployeeID));
      
        // bind new values
        $stmt->bindParam(":SickDaysUsed", $this->SickDaysUsed);
        $stmt->bindParam(":SickDaysRemaining", $this->SickDaysRemaining);
        $stmt->bindParam(":VacationDaysUsed", $this->VacationDaysUsed);
        $stmt->bindParam(":VacationDaysRemaining", $this->VacationDaysRemaining);
        $stmt->bindParam(":EmployeeID", $this->EmployeeID);
      
        // execute the query
        if($stmt->execute()){
            return true;
        }
      
        return false;
    }
    function checkAvailability($SchedDate){
  
        // select all query
        $query = "SELECT*FROM " . $this->table_name . " WHERE EmployeeID
       NOT IN (SELECT EmployeeID FROM Requests
       WHERE EmployeeInfo.EmployeeID = Requests.EmployeeID 
       AND DateRequested = ?
       AND Status = 'approved')";
      
        // prepare query statement
        $stmt = $this->conn->prepare($query);
      
        // sanitize
        $SchedDate=htmlspecialchars(strip_tags($SchedDate));
        $SchedDate = "$SchedDate";
      
        // bind
        $stmt->bindParam(1, $SchedDate);
      
        // execute query
        $stmt->execute();
      
        return $stmt;
    }

}
?>
