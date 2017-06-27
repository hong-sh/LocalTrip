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



$sql = "SELECT course_date.course_date FROM course_date"
		." WHERE course_date.course_idx = $_GET[course_idx] AND course_date.course_date BETWEEN '$_GET[start]' AND '$_GET[end]'";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	while($row = mysql_fetch_array($query, MYSQL_ASSOC)) {
		$course_date[] = $row;
	}
	$result->course_date_list = $course_date;
	
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>