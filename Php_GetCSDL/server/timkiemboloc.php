<?php
//header("Content-Type: application/json");
include "connect.php";

$gia1 = $_POST['gia1'] ;
$gia2 = $_POST['gia2'] ;
$iddm = $_POST['iddm']; 
$sapxep = $_POST['sapxep'];

if(isset($_POST['gia1']) and isset($_POST['gia2']))
{
    $query = "SELECT * FROM `sanpham` WHERE giabansp > ".$gia1." AND giabansp < ".$gia2." AND iddm ".$iddm." ORDER BY `giabansp` ".$sapxep." ";
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
    		];	
    }
    	
    print_r(json_encode($arr)); 

}
else
{
    
	$arr = [
    	'success' => false,
    		];	
	print_r(json_encode($arr)); 
}



?> 