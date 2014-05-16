<?php

$operacion =$_POST['codigo'];

switch($operacion) {
    case 0:
        echo getUsers();
        break;
    case 1:
        $imei = $_POST['IMEI'];
        $regid = $_POST['regid'];
        addRegistration($imei, $regid);
        break;
    case 2:
        $cabecera = array(
            'Authorization: key=AIzaSyDfgT87fvHf9E2Ad_wQsLEmjEkdafiHPvY',
            'Content-type: Application/json');
        //recogemos el mensaje que se envia
        $data = array('my_message' => $_POST['my_message'] );
        #llamamos siempre al mismo registration id, para ver que funciona

        $regid = obtenerRegidDesdeIMEI($_POST['toUser']);
        
        $info = array(
            'registration_ids' => array($regid),
            'data' => $data); 
        peticionCurl($info, $cabecera);
        break;
    case 3:
        $cabecera = array(
            'Authorization: key=AIzaSyDfgT87fvHf9E2Ad_wQsLEmjEkdafiHPvY',
            'Content-type: Application/json');
        $data = array('latitude' => $_POST['latitude'],
            'longitude' => $_POST['longitude'],
            'isMap' => true);
        $regid = obtenerRegidDesdeIMEI($_POST['toUser']);
        $info = array(
            'registration_ids' => array($regid),
            'data' => $data);
        peticionCurl($info, $cabecera);
        break;

}

function peticionCurl($info, $cabecera) {
    $ch = curl_init(); #inicializar el handler de curl

    #indicar el destino de la peticion, el servicio GCM de google
    curl_setopt($ch, CURLOPT_URL, 'https://android.googleapis.com/gcm/send');

    #indicar que la conexion es de tipo POST
    curl_setopt($ch, CURLOPT_POST, true);

    #agregar las cabeceras
    curl_setopt($ch, CURLOPT_HTTPHEADER, $cabecera);

    #indicar que se desea recibir la respuesta a la conexion en forma de string
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($info));

    #ejecutar la llamada
    echo $resultado = curl_exec($ch);

    #cerrar el handler de curl
    curl_close($ch);
}

function getUsers() {
    $dbh = conectaDB();
	$consult_registro= $dbh->prepare("SELECT usuario FROM registro");
	$consult_registro->execute();
	$data_proximas = $consult_registro->fetchAll();
	if (!empty($data_proximas)) {
		return json_encode($data_proximas);
	} else {
		return null;
	}
}

function obtenerRegidDesdeIMEI($toUser) {
    $dbh = conectaDB();
	$consult_registro= $dbh->prepare("SELECT regid FROM registro where usuario=:imei");
    $consult_registro->bindParam(':imei', $toUser, PDO::PARAM_STR);
	$consult_registro->execute();
	$data_proximas = $consult_registro->fetch();
	if (!empty($data_proximas)) {
		return $data_proximas['regid'];
	} else {
		return null;
	}

}

function conectaDB() {
    $hostname = 'localhost';
    $username = 'Xragudo001';
    $password = 'I9tPTsWi55';
    try {
        $db = new PDO("mysql:host=$hostname;dbname=Xragudo001_NinjaMessaging", $username, $password);
        $db->exec("set names utf8");
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return($db);
    } catch (PDOException $e) {
        echo $e->getMessage();
        exit();
    }
}

 
function addRegistration($imei, $regid) {
    $dbh = conectaDB();
    $consult_registro=$dbh->prepare("INSERT INTO registro (usuario, regid) VALUES (:usu, :regid)");
    $consult_registro->bindParam(':usu', $imei, PDO::PARAM_STR);
    $consult_registro->bindParam(':regid', $regid, PDO::PARAM_STR);
    $consult_registro->execute();
}
?>
