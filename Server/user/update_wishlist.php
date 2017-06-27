<?php 
header("Content-Type:text/html;charset=utf-8");

$con = mysql_connect("localhost", "rkskekfk2020", "Tjdgkr23!");

if(!$con) {
	die('Could not connect:' .mysql_error());
}

mysql_select_db("rkskekfk2020_jeju", $con);
mysql_query("set names utf-8");
mysql_query("set session character_set_connection=utf8;");
mysql_query("set session character_set_results=utf8;");
mysql_query("set session character_set_client=utf8;");

$course_idx = $_GET[course_idx];
$user_idx = $_GET[user_idx];

$select_query = "SELECT * FROM wishlist WHERE course_idx = ".$course_idx ." AND user_idx = ".$user_idx ."";

if(!($query = mysql_query($select_query, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	if(mysql_num_rows($query) > 0) {
		$delete_query = "DELETE FROM wishlist WHERE course_idx = " .$course_idx ." AND user_idx = " .$user_idx;
		if(!mysql_query($delete_query, $con))
			$result->action = "failure";
		else
			$result->action = "delete";
	}
	else {
		$insert_query = "INSERT INTO wishlist (course_idx, user_idx) VALUES (" .$course_idx .", " .$user_idx .")";
		if(!mysql_query($insert_query, $con))
			$result->action = "failure";
		else
			$result->action = "insert";
	}
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>