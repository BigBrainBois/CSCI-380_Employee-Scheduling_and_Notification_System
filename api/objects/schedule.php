<?php
class Schedule{
    private $conn;
    private $table_name = "Schedule";

    public $ID;
    public $Date;
    public $EmployeeID;
    public $StartTime;
    public $EndTime;


    public function __construct($db){
        $this->conn = $db;
    }

    function readSchedule(){

        //creating querry
        $query = "SELECT Date, ScheduleID, EmployeeID, 
        StartTime, EndTime
        FROM " . $this->table_name . " 
        ORDER BY Date";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //executing querry
        $stmt->execute();
        return $stmt;

    }

    function createSchedule(){
        //creating insert querry
        $query = "INSERT INTO " . $this->table_name . "
        SET Date =:Date, EmployeeID =:EmployeeID,  
        StartTime =:StartTime, EndTime =:EndTime ";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //cleaning data
        $this->Date = htmlspecialchars(strip_tags($this->Date));
        $this->EmployeeID = htmlspecialchars(strip_tags($this->EmployeeID));
        $this->StartTime = htmlspecialchars(strip_tags($this->StartTime));
        $this->EndTime = htmlspecialchars(strip_tags($this->EndTime));

        //binding data to querry
        $stmt->bindParam(":Date",$this->Date);
        $stmt->bindParam(":EmployeeID",$this->EmployeeID);
        $stmt->bindParam(":StartTime",$this->StartTime);
        $stmt->bindParam(":EndTime",$this->EndTime);

        if($stmt->execute()){
            return true;
        }
        
        return false;

    }
    // update the request
    function updateSchedule(){
  
        // update query
        $query = "UPDATE
                    " . $this->table_name . "
                SET
                    Date =:Date,
                    EmployeeID =:EmployeeID,
                    StartTime =:StartTime
                    EndTime =:EndTime
                WHERE
                    ID =:ID";
      
        // prepare query statement
        $stmt = $this->conn->prepare($query);
      
        // sanitize
        $this->Date=htmlspecialchars(strip_tags($this->Date));
        $this->EmployeeID=htmlspecialchars(strip_tags($this->EmployeeID));
        $this->StartTime=htmlspecialchars(strip_tags($this->StartTime));
        $this->EndTime=htmlspecialchars(strip_tags($this->EndTime));
        $this->ID=htmlspecialchars(strip_tags($this->ID));
      
        // bind new values
        $stmt->bindParam(":Date",$this->Date);
        $stmt->bindParam(":EmployeeID",$this->EmployeeID);
        $stmt->bindParam(":StartTime",$this->StartTime);
        $stmt->bindParam(":EndTime",$this->EndTime);
        $stmt->bindParam(":ID",$this->ID);

        // execute the query
        if($stmt->execute()){
            return true;
        }
      
        return false;
    }

}
?>