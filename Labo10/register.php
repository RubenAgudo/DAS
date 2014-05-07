<?php
    $telefono = $_GET['telefono'];
conectaDB();
print_r(getNexts()); 

function conectaDB() {
$hostname = 'localhost';
$username = 'labo9';
$password = '1234';
try {
    $db = new PDO("mysql:host=$hostname;dbname=labo10", $username, $password);
    $db->exec("set names utf8");
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    return($db);
} catch (PDOException $e) {
    echo $e->getMessage();
    exit();
}
}
function getNexts() {
	$dbh = conectaDB();
	$consult_registro= $dbh->prepare("SELECT * FROM registro where telefono=:telf");
    $consult_registro->bindParam(':telf', $telefono, PDO::PARAM_STR);
	$consult_registro->execute();
	$data_proximas = $consult_registro->fetch();
	if (!empty($data_proximas)) {
		return $data_proximas;
	} else {
		return 0;
	}
}
 
?>
