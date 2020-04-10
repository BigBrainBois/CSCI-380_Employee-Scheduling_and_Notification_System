<?php
class User{
    private $conn;
    private $table_name = "Users";

    public $Username;
    public $Name;
    public $Email;
    public $Password;
    public $StartDate;
    public $EmployeeID;

    public function __construct($db){
        $this->conn = $db;
    }

    function read(){
        $querry = "SELECT Username, Name, Email, StartDate, EmployeeID FROM Users";

        $stmt = $this->conn->prepare($querry);
        $stmt->execute();
        return $stmt;

    }
    
}
?>
