<?php

//header("Content-Type: application/json");
include "connect.php";

$tenuser = $_POST['tenuser'];
$email = $_POST['email'] ;
$pass = $_POST['pass'];
$sdt = $_POST['sdt'];
$gioitinh = $_POST['gioitinh'];


$query = 'SELECT * FROM `userstore` WHERE `email` = "'.$email.'" ';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

if($numrow > 0)
{
	$arr = [
		'success' => false,
		'message' => 'tai khoan da ton tai'
	];	
}
else
{
	$query = 'INSERT INTO `userstore`(`tenuser`, `anhdaidien`, `email`, `sdt`, `pass`,`gioitinh`) VALUES ("'.$tenuser.'","","'.$email.'","'.$sdt.'","'.$pass.'","'.$gioitinh.'")';

	$data = mysqli_query($conn, $query);

	if ($data == true) {

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong',
			
	];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}


}

print_r(json_encode($arr));

?> 




