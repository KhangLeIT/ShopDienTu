<?php
//header("Content-Type: application/json");
include "connect.php";

$email = $_POST['email'] ;
$pass = $_POST['pass'];


$query = 'SELECT * FROM `userstore` where `email` = "'.$email.'" AND `pass` = "'.$pass.'"' ;
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}

if (!empty($result)) {

	$arr = [
		'success' => true,
		'message' => 'Thanh Cong',
		'result'  => $result	
	];	
}else{
	$query = 'SELECT * FROM `userstore` WHERE `email` = "'.$email.'" ';
	$data = mysqli_query($conn, $query);
	$numrow = mysqli_num_rows($data);

	if($numrow > 0)
	{
		$arr = [
			'success' => false,
			'message' => 'mật khẩu không đúng'
		];	
	}
	else
	{
		$arr = [
			'success' => false,
			'message' => 'tài khoản không tồn tại'
		];
	}
}
	
print_r(json_encode($arr));

?> 