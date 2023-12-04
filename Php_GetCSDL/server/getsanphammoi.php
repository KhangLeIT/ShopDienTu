
<?php
//header("Content-Type: application/json");
$action = isset($_GET['action'])?$_GET['action']:'';

switch ($action) {
	case 'getall':
		getall();
		break;
	case 'checkemail':
		check_email();
		break;
	
	case 'getiddm':
		getiddm();
		break;

	default:
		getspmoi();
		break;
}

function getiddm(){
	// echo json_encode(array('success'=>true,'id'=>$id));
	$id = isset($_GET['iddm'])?$_GET['iddm']:1;
	include "connect.php";
	$query = "SELECT *  FROM `sanpham` where iddm = {$id} LIMIT 0,5 ";
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
		$arr = [
			'success' => false,
			// 'message' => 'Khong Thanh Cong',
			// 'result'  => $result	
		];
	}
	print_r(json_encode($arr));

}


function check_email ()
{

	$email = isset($_GET['email'])?$_GET['email']:"";
	include "connect.php";
	$query = 'SELECT * FROM `userstore` WHERE `email` = "'.$email.'" ';
	$arr = array();
	$data = mysqli_query($conn, $query);

	$numrow = mysqli_num_rows($data);

	if($numrow>0)
	{
		$arr = [
			'success' => false,
			'message' => 'tai khoan da ton tai'
		];	

	}
	else
	{
		$arr = [
			'success' => true,
			'message' => 'ok!'
		];	
	}

	
	print_r(json_encode($arr));

}
function getspmoi()
{
	include "connect.php";
	$query = "SELECT *  FROM `sanpham` ORDER BY idsp DESC LIMIT 0,10";
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
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong',
			'result'  => $result	
		];
	}
	print_r(json_encode($arr));
}

function getall(){
	include "connect.php";
	$query = "SELECT *  FROM `sanpham` ORDER BY idsp DESC";
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
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong',
			'result'  => $result	
		];
	}
	print_r(json_encode($arr));

}

?> 