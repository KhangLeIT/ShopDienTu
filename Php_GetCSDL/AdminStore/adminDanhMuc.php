<?php
//header("Content-Type: application/json");
include "connect.php";
$action = $_POST['action'];
$arr = [
			'success' => false,
			'message' => 'error',	
		];

switch ($action) {
	case "getloaisp":
		getloaisp();
		break;
	case "themdanhmuc":
		themdanhmuc();
		break;
	case "delete":
		deleteDanhMuc();
		break;
	case "backDelete":
		backDanhMucDelete();
		break;
	case "update":
		updateDanhMuc();
		break;
	
	default:
		defaut();
		break;
}

function getloaisp(){
	include "connect.php";
	$query = "SELECT * FROM `loaisanpham`";
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

function themdanhmuc(){
	include "connect.php";
	$tenDM = $_POST['tenDM'];
	$hinhanh = $_POST['hinhanh'];

	$query = 'INSERT INTO `loaisanpham`(`tensanpham`, `hinhanh`) VALUES ("'.$tenDM.'","'.$hinhanh.'")';
	$data = mysqli_query($conn, $query);
	if($data == true){
		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
	
		];	
	}
	else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}
	print_r(json_encode($arr));

}

function deleteDanhMuc(){
	include "connect.php";
	$iddm = $_POST['iddm'];
	$target_dir = "../Hinhanh/";

	$query = "SELECT `hinhanh` FROM `loaisanpham` WHERE iddm =".$iddm." LIMIT 1";

	$data = mysqli_query($conn, $query);
	$result = array();

	while ($row = mysqli_fetch_assoc($data)) {
		$result = ($row);
		// code...
	}

	if(!empty($result)){

		$hinhanh = $result['hinhanh'];
		// echo $hinhanh;
        // di chuyen hinh anh sang backup
        $source_file = '../Hinhanh/'.$hinhanh.'';
        $destination_path = '../backupHinhanh/';
        rename($source_file, $destination_path . pathinfo($source_file, PATHINFO_BASENAME));
        
		$queryDelete = 'DELETE FROM `loaisanpham` WHERE `iddm` = '.$iddm.'';
		$data = mysqli_query($conn, $queryDelete);

		if($data == true){
			$arr = [
				'success' => true,
				'message' => 'Thanh Cong'
		
			];	
		}
		else{
			$arr = [
				'success' => false,
				'message' => 'Khong Thanh Cong'
			];
		}

	}
	else{
		$arr = [
				'success' => false,
				'message' => 'eror'
			];
	}
	print_r(json_encode($arr));
}

function backDanhMucDelete(){
	include "connect.php";
	$iddm = $_POST['iddm'];
	$hinhanh = $_POST['hinhanh'];
	$tenDM = $_POST['tenDM'];

	$query = 'INSERT INTO `loaisanpham`(`iddm`, `tensanpham`, `hinhanh`) VALUES ("'.$iddm.'","'.$tenDM.'","'.$hinhanh.'")';
	$data = mysqli_query($conn, $query);

	if($data == true){
		$source_file = '../backupHinhanh/'.$hinhanh.'';
        $destination_path = '../Hinhanh/';
        rename($source_file, $destination_path . pathinfo($source_file, PATHINFO_BASENAME));

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
	
		];	
	}
	else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}
	print_r(json_encode($arr));
}

function updateDanhMuc(){
	include "connect.php";
	$iddm = $_POST['iddm'];
	$querySet = $_POST['querySet'];
	$query = 'UPDATE `loaisanpham` SET iddm = "'.$iddm.'" '.$querySet.' WHERE iddm = "'.$iddm.'"';
	$data = mysqli_query($conn, $query);

	if($data == true){
		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
	
		];	
	}
	else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}
	print_r(json_encode($arr));
}

function defaut(){
	print_r(json_encode($arr));
}

?> 