
<?php

$elegirFuncion = $_GET['funcion'];
switch($elegirFuncion) {
    case 0:
        getUsers();
        break;
    case 1:
        break;
    case 2:
        break;
    default:
        echo 0;
}

function conectaDB() {
	$hostname = 'localhost';
	$username = 'labo9';
	$password = '1234';
    $dbname = 'labo9';
	try {
		$db = new PDO("mysql:host=$hostname;dbname=$dbname", $username, $password);
		$db->exec("set names utf8");
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		return($db);
	} catch (PDOException $e) {
		echo $e->getMessage();
		exit();
	}
}

function getUsers() {
    

	$dbh = conectaDB();
	$consult_families = $dbh->prepare("SELECT * FROM Usuarios"); 
	$consult_families->execute();
	$data_families = $consult_families->fetchAll();
    echo $data_families['TlfnoUsuario'];
    $test = [1,2,3,4,5,6];

    foreach($num as $test) {
        echo $num;
    }
}
