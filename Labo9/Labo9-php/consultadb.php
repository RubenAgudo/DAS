<?php
$parametro = $_POST['actualizar'];
switch ($parametro) {
	case '0':
		$proximas = getNexts();
		echo json_encode($proximas);
		//echo 'la proxima subasta es: '.$proximas[0]['nombre_casa'];
		//echo 'ly la siguiente es: '.$proximas[1]['nombre_casa'];
		break;
	
	default:
		echo 0;
		break;
}

function conectaDB() {
	$hostname = 'localhost';
	$username = 'root';
	$password = '1234';
	try {
		$db = new PDO("mysql:host=$hostname;dbname=SUBASTAS", $username, $password);
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
	$consult_proximas = $dbh->prepare("SELECT * FROM proximas");
	$consult_proximas->execute();
	$data_proximas = $consult_proximas->fetchAll();
	if (!empty($data_proximas)) {
		return $data_proximas;
	} else {
		return 0;
	}
}
?>