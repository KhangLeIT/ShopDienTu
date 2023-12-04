<?php

include "connect.php";
$target_dir = "../Hinhanh/";
$target_file_name = $target_dir.basename($_FILES["file"]["name"]);
$response = array();

if(isset($_FILES["file"]))
{
	if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))
	{
		$arr =[
			'success' => true,
			'message' =>"thanh cong"
		];

	}
	else{
		$arr =[
			'success' => false,
			'message' =>"ko thanh cong"
		];

	}
}
else
{
	$arr =[
			'success' => false,
			'message' =>"loi"
		];
}

echo json_encode($arr);
?>