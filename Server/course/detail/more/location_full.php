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

$sql = "SELECT location.*, detail_course.detail_course_image FROM location INNER JOIN detail_course ON location.location_idx = detail_course.detail_course_location_idx"
		." WHERE location_idx = $_GET[location_idx]";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	$result->location_info = mysql_fetch_object($query);
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>