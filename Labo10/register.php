<?php
$telefono = $_GET['telefono'];
$operacion =$_GET['operacion'];

switch($operacion) {
case 0:
    echo checkIfExistsRegistration($telefono);
    break;
case 1:
    addRegistration($telefono, $_GET['regid']);
    break;
}

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

function checkIfExistsRegistration($telefono) {
    $dbh = conectaDB();
	$consult_registro= $dbh->prepare("SELECT regid FROM registro where telefono=:telf");
    $consult_registro->bindParam(':telf', $telefono, PDO::PARAM_STR);
	$consult_registro->execute();
	$data_proximas = $consult_registro->fetch();
	if (!empty($data_proximas)) {
		return $data_proximas['regid'];
	} else {
		return null;
	}
}
 
function addRegistration($telefono, $regid) {
    $dbh = conectaDB();
    $consult_registro=$dbh->prepare("INSERT INTO registro (telefono, regid) VALUES (:telf, :regid)");
    $consult_registro->bindParam(':telf', $telefono, PDO::PARAM_STR);
    $consult_registro->bindParam(':regid', $regid, PDO::PARAM_STR);
    $consult_registro->execute();
}
?>
