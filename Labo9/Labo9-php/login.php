<?php
echo 'hola php';
$parametro = $_GET['parametro'];
switch ($parametro) {
    case '0':
        $usuarios = getUsers();
        echo 'el telefono es: '.$usuarios[0];
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
        $db = new PDO("mysql:host=$hostname;dbname=LABORATORIO9", $username, $password);
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
    $consult_usuarios = $dbh->prepare("SELECT * FROM usuarios");
    $consult_usuarios->execute();
    $data_usuarios = $consult_usuarios->fetch();
    if (!empty($data_usuarios)) return $data_usuarios;
    else return 0;
}
?> 
