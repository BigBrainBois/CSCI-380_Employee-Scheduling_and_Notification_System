<?php
class Database{
    private $host = "[2610:1c1:0:17::e:7620]"
    private $db_name = "SchedulingApp";
    private $username = "root"
    private $password = "KLAMchowDer1"
    public $conn;

    public function getConnection(){
        $this->con = null;
        
        try{
            $this->conn = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->db_name, $this->username, $this->password);
            $this->conn->exec("set names utf8");
        }
        catch(PDOException $exception){
            echo "Connection error: " . $exception->getMessage();
        }

        return $conn;
    }


}
?>