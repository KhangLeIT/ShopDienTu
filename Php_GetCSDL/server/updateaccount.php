<?php
//header("Content-Type: application/json");
include "connect.php";

$tenuser = $_POST['tenuser'] ;
$sdt = $_POST['sdt'];
$iduser = $_POST['iduser'];


$query = 'UPDATE `userstore` SET `sdt` = "'.$sdt.'", `tenuser` ="'.$tenuser.'" WHERE `userstore`.`iduser` = "'.$iduser.'"' ;
$data = mysqli_query($conn, $query);


if ($data == true) {

	$arr = [
		'success' => true,
		'message' => 'Thanh Cong',	
	];	
}else{
	$arr = [
		'success' => false,
		'message' => 'error',	
	];
	
}
	print_r(json_encode($arr));

?> 