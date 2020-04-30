<?php
class Request{
    private $conn;
    private $table_name = "Requests";

    public $RequestID;
    public $EmployeeID;
    public $DateRequested;
    public $Status;
    public $RequestType;
    public $Message;


    public function __construct($db){
        $this->conn = $db;
    }

    function readRequest(){

        //creating querry
        $query = "SELECT RequestID, EmployeeID, DateRequested, 
        Status, RequestType, Message
        FROM " . $this->table_name . "";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //executing querry
        $stmt->execute();
        return $stmt;

    }

    function createRequest(){
        //creating insert querry
        $query = "INSERT INTO " . $this->table_name . "
        SET RequestID =:RequestID, EmployeeID =:EmployeeID, DateRequested =:DateRequested, 
        Status =:Status, RequestType =:RequestType, Message =:Message";

        //preparing querry
        $stmt = $this->conn->prepare($query);

        //cleaning data
        $this->RequestID = htmlspecialchars(strip_tags($this->RequestID));
        $this->EmployeeID = htmlspecialchars(strip_tags($this->EmployeeID));
        $this->DateRequested = htmlspecialchars(strip_tags($this->DateRequested));
        $this->Status = htmlspecialchars(strip_tags($this->Status));
        $this->RequestType = htmlspecialchars(strip_tags($this->RequestType));
        $this->Message = htmlspecialchars(strip_tags($this->Message));

        //binding data to querry
        $stmt->bindParam(":RequestID",$this->RequestID);
        $stmt->bindParam(":EmployeeID",$this->EmployeeID);
        $stmt->bindParam(":DateRequested",$this->DateRequested);
        $stmt->bindParam(":Status",$this->Status);
        $stmt->bindParam(":RequestType",$this->RequestType);
        $stmt->bindParam(":Message",$this->Message);

        if($stmt->execute()){
            return true;
        }
        
        return false;

    }
    // update the request
    function updateRequest(){
  
        // update query
        $query = "UPDATE
                    " . $this->table_name . "
                SET
                    EmployeeID =:EmployeeID,
                    DateRequested =:DateRequested,
                    Status =:Status,
                    RequestType =:RequestType,
                    Message =:Message
                WHERE
                   RequestID =:RequestID";
      
        // prepare query statement
        $stmt = $this->conn->prepare($query);
      
        // sanitize
        $this->EmployeeID=htmlspecialchars(strip_tags($this->EmployeeID));
        $this->DateRequested=htmlspecialchars(strip_tags($this->DateRequested));
        $this->Status=htmlspecialchars(strip_tags($this->Status));
        $this->RequestType=htmlspecialchars(strip_tags($this->RequestType));
        $this->Message=htmlspecialchars(strip_tags($this->Message));
        $this->RequestID=htmlspecialchars(strip_tags($this->RequestID));
      
        // bind new values
        $stmt->bindParam(":EmployeeID",$this->EmployeeID);
        $stmt->bindParam(":DateRequested",$this->DateRequested);
        $stmt->bindParam(":Status",$this->Status);
        $stmt->bindParam(":RequestType",$this->RequestType);
        $stmt->bindParam(":Message",$this->Message);
        $stmt->bindParam(":RequestID",$this->RequestID);

        // execute the query
        if($stmt->execute()){
            return true;
        }
      
        return false;
    }

}
?>