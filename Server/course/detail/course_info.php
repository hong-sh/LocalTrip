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

$sql = "SELECT course_name, course_image, course_price_min, course_time, course_drive, course_meeting_location, course_meeting_location_lat, course_meeting_location_lng, course_meeting_location_image,"
		." course_meeting_time, course_option, course_nonoption, course_etc, course_info, course_total_rate, course_total_review FROM course WHERE course_idx = $_GET[course_idx]";

if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	$result->course_info = mysql_fetch_object($query);
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>