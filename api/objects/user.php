<?php
class User{
    private $conn;
    private $table_name = "Users";

    public $user_name;
    public $name;
    public $email;
    public $password;
    public $start_date;
    public $employee_id;

    public function __construct($db){
        $this->conn = $db;
    }
    
}
?>